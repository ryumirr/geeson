package kafka.inventory.consumer;

import app.inventory.app.IdempotencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.entity.OutboxJpaEntity;
import domain.inventory.domain.repository.OutboxRepository;
import grpc.client.InventoryReservationGrpcClient;
import grpc.client.InventoryGrpcClient;
import grpc.client.PurchaseOrderGrpcClient;
import grpc.client.StockMovementGrpcClient;
import grpc.client.WarehouseGrpcClient;
import grpc.inventory.InventoryReservation;
import grpc.inventory.SelectInventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import support.messaging.command.OrderStartPayload;
import support.messaging.command.ShipmentReadyPayload;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final IdempotencyService idempotencyService;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;
    private final InventoryReservationGrpcClient inventoryReservationGrpcClient;
    private final StockMovementGrpcClient stockMovementGrpcClient;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final WarehouseGrpcClient warehouseGrpcClient;
    private final PurchaseOrderGrpcClient purchaseOrderGrpcClient;

    /**
     * 주문 생성 성공 이벤트 (ord-ord-req-succ-event)를 consume 해서
     * 재고 예약 및 관련 처리만 수행한다.
     */
    @KafkaListener(
            topics = "ord-ord-req-succ-event",
            groupId = "inventory-consumer-group"
    )
    public void handleOrderCreated(String message) {
        log.info("📨 [Inventory] Received Kafka message: {}", message);

        String idempotencyKey = null;
        try {
            // 1. Kafka Payload 역직렬화
            OrderStartPayload payload = objectMapper.readValue(message, OrderStartPayload.class);
            log.info("📦 Parsed Order Event: orderId={}, totalPrice={}, items={}",
                    payload.orderId(), payload.totalPrice(), payload.items());

            long orderId = Long.parseLong(payload.orderId());
            idempotencyKey = "INVENTORY-RESERVATION-" + orderId;

            // 2. 중복 처리 체크
            if (idempotencyService.isAlreadyProcessed(idempotencyKey)) {
                log.info("✅ Duplicate inventory event, skip. orderId={}", orderId);
                return;
            }

            idempotencyService.markAsProcessing(idempotencyKey, "ord-ord-req-succ-event");

            // 3. 주문에 대해 기존 재고 예약이 이미 있는지 확인 (gRPC 레벨 이중 체크)
            List<InventoryReservation> reservations =
                    inventoryReservationGrpcClient.getReservationsByOrder(orderId);

            if (!reservations.isEmpty() && reservations.get(0).getReservationId() != 0L) {
                log.info("✅ Existing inventory reservation found. orderId={}, reservationId={}",
                        orderId, reservations.get(0).getReservationId());
                idempotencyService.markAsCompleted(idempotencyKey);
                return;
            }

            // 현재는 첫 번째 아이템 기준으로 오케스트레이션한다.
            // 여러 아이템 처리로 확장하더라도 orderId 기준 idempotency는 유지한다.
            var item = payload.items().get(0);

            long productId = Long.parseLong(item.productId());
            int quantity = item.quantity();

            // 3-1. 사용 가능한 재고 조회 (gRPC - inventory-api)
            SelectInventoryResponse inventoryResponse =
                    inventoryGrpcClient.selectInventory(productId, quantity);

            var inventory = inventoryResponse.getInventory();
            log.info("🔎 Inventory selected. productId={}, inventoryId={}, availableQty={}",
                    productId, inventory.getInventoryId(), inventory.getAvailableQuantity());

             // @todo 2025/11/10 윗줄까지 테스트 완료
            // 3-2. 재고 예약 생성
            InventoryReservation reservationResult =
                    inventoryReservationGrpcClient.createReservation(
                            inventory.getInventoryId(),
                            orderId,
                            quantity,
                            300 // TTL seconds (예시)
                    );

            if ("RESERVED".equals(reservationResult.getStatus())) {
                String stockOutReference = "ORDER-" + orderId;

                // 4. 재고 이동(StockMovement) 등록
                stockMovementGrpcClient.recordStockOut(
                        inventory.getInventoryId(),
                        quantity,
                        stockOutReference,
                        "Shipment stock-out for order " + orderId
                );

                // 5. 출고 성공 이후 예약을 확정 상태로 전이한다.
                boolean reservationCompleted = inventoryReservationGrpcClient
                        .completeReservation(reservationResult.getReservationId());
                if (!reservationCompleted) {
                    throw new IllegalStateException(
                            "Reservation completion failed after stock-out. reservationId="
                                    + reservationResult.getReservationId());
                }

                // 6. 창고 정보 조회
                var warehouseResponse = warehouseGrpcClient.getWarehouse(inventory.getWarehouseId());
                log.info("🏭 Warehouse info. warehouseId={}, name={}, location={}",
                        warehouseResponse.getWarehouseId(), warehouseResponse.getName(), warehouseResponse.getLocation());

                // 7. 해당 창고의 발주 정보 조회
                var purchaseOrdersResponse = purchaseOrderGrpcClient.listPurchaseOrders(
                        null, null, warehouseResponse.getWarehouseId(), null);
                log.info("📋 Purchase orders for warehouseId={}. count={}",
                        warehouseResponse.getWarehouseId(), purchaseOrdersResponse.getPurchaseOrdersCount());

                idempotencyService.markAsCompleted(idempotencyKey);
                log.info("✅ Shipment stock-out completed. orderId={}, reservationId={}, warehouseId={}",
                        orderId, reservationResult.getReservationId(), inventory.getWarehouseId());

                // 8. Outbox 테이블에 저장 → 스케줄러가 Kafka로 발행
                ShipmentReadyPayload shipmentPayload = new ShipmentReadyPayload(orderId);
                outboxRepository.save(OutboxJpaEntity.create(
                        "SHIPMENT_READY",
                        "ord-ord-ship-succ-event",
                        objectMapper.writeValueAsString(shipmentPayload)
                ));
                log.info("📥 Outbox event saved. orderId={}", orderId);

            } else {
                log.warn("⚠️ Inventory reservation failed. orderId={}, status={}",
                        orderId, reservationResult.getStatus());

                // @todo 재고 부족/예약 실패 이벤트 발행
                // InventoryReserveFailedEvent
            }

        } catch (Exception e) {
            log.error("❌ [Inventory] Failed to handle order-created event", e);
            if (idempotencyKey != null) {
                idempotencyService.markAsFailed(idempotencyKey, e.getMessage());
            }
            // @todo  실패 이벤트 발행 등의 보상 로직
        }
    }
}
