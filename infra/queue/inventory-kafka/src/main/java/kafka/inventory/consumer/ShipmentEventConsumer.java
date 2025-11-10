package kafka.inventory.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import grpc.client.InventoryGrpcClient;
import grpc.client.InventoryReservationGrpcClient;
import grpc.client.StockMovementGrpcClient;
import grpc.client.WarehouseGrpcClient;
import grpc.inventory.AddStockMovementRequest;
import grpc.inventory.InventoryReservation;
import grpc.inventory.SelectInventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import support.messaging.command.OrderStartPayload;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final ObjectMapper objectMapper;
    private final InventoryReservationGrpcClient inventoryReservationGrpcClient;
    private final StockMovementGrpcClient stockMovementGrpcClient;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final WarehouseGrpcClient warehouseGrpcClient;
    // 필요하면 여기서 Inventory 이벤트 Publisher 주입해서 성공/실패 이벤트 발행

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

        try {
            // 1. Kafka Payload 역직렬화
            OrderStartPayload payload = objectMapper.readValue(message, OrderStartPayload.class);
            log.info("📦 Parsed Order Event: orderId={}, totalPrice={}, items={}",
                    payload.orderId(), payload.totalPrice(), payload.items());

            long orderId = Long.parseLong(payload.orderId());

            // 2. 주문에 대해 기존 재고 예약이 이미 있는지 확인
            List<InventoryReservation> reservations =
                    inventoryReservationGrpcClient.getReservationsByOrder(orderId);

            if (!reservations.isEmpty() && reservations.get(0).getReservationId() != 0L) {
                log.info("✅ Existing inventory reservation found. orderId={}, reservationId={}",
                        orderId, reservations.get(0).getReservationId());
                return;
            }

            // 3. (간단 버전) 첫 번째 아이템 기준으로 재고 선택 및 예약
            //    필요하면 payload.items() 전체 loop 돌리도록 확장 가능
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
                            inventory.getProductId(),
                            quantity,
                            300 // TTL seconds (예시)
                    );

            if ("RESERVED".equals(reservationResult.getStatus())) {
                // 4. 재고 이동(StockMovement) 등록
                stockMovementGrpcClient.addStockMovement(
                        AddStockMovementRequest.newBuilder()
                                .setInventoryId(inventory.getInventoryId())
                                .setQuantity(quantity)
                                .setReferenceId("ORDER-" + orderId)
                                .build()
                );

                // 5. 창고 정보 조회 (감사/로그 용도)
                warehouseGrpcClient.getWarehouse(inventory.getWarehouseId());

                log.info("✅ Inventory reserved successfully. orderId={}, reservationId={}",
                        orderId, reservationResult.getReservationId());

                // 6. TODO: 여기서 "재고 예약 성공" 이벤트 발행 (ord-inv-dec-succ-event 등)
                // inventoryEventPublisher.publish(new InventoryReserveSucceedEvent(...));

            } else {
                log.warn("⚠️ Inventory reservation failed. orderId={}, status={}",
                        orderId, reservationResult.getStatus());

                // TODO: 재고 부족/예약 실패 이벤트 발행
                // inventoryEventPublisher.publish(new InventoryReserveFailedEvent(orderId, ...));
            }

        } catch (Exception e) {
            log.error("❌ [Inventory] Failed to handle order-created event", e);
            // TODO: 실패 이벤트 발행 등의 보상 로직
        }
    }
}
