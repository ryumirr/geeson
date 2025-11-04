package kafka.order.consumer;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import support.messaging.command.OrderStartPayload;
import grpc.client.*;
import grpc.inventory.*;
import grpc.purchaseorder.*;
import grpc.purchaseorder.GetPurchaseOrderResponse;
import grpc.shipment.GetShipmentResponse;
import support.messaging.event.InventoryReserveFailedEvent;
import support.messaging.event.InventoryReserveSucceedEvent;
import domain.order.message.OrderEventPublisher;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ShipmentGrpcClient shipmentGrpcClient;
    private final InventoryReservationGrpcClient inventoryReservationGrpcClient;
    private final StockMovementGrpcClient stockMovementGrpcClient;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final WarehouseGrpcClient warehouseGrpcClient;
    private final PurchaseOrderGrpcClient purchaseOrderGrpcClient;
    private final OrderEventPublisher orderEventPublisher;

    @KafkaListener(topics = "ord-ord-req-succ-event", groupId = "shipment-group")
    public void handleOrderCreated(String message) {
        try {
            // =============================
            // ① Kafka Payload Deserialize
            // =============================
            OrderStartPayload payload = objectMapper.readValue(message, OrderStartPayload.class);
            log.info("📦 Received Order Event: orderId={}, totalPrice={}, items={}",
                    payload.orderId(), payload.totalPrice(), payload.items());

            long orderId = Long.parseLong(payload.orderId());

            // =============================
            // ② Create Shipment
            // =============================
            shipmentGrpcClient.createShipment(String.valueOf(orderId), "TRACKING-" + orderId);

            log.info("🚚 Shipment created for orderId={}", orderId);

            // =============================
            // ③ Check existing inventory reservations
            // =============================
            List<InventoryReservation> reservations =
                    inventoryReservationGrpcClient.getReservationsByOrder(orderId);

            if (!reservations.isEmpty() && reservations.get(0).getReservationId() != 0L) {
                log.info("✅ Inventory reservation already exists: reservationId={}",
                        reservations.get(0).getReservationId());
                return;
            }

            // =============================
            // ④ Try inventory reservation
            // =============================
            var item = payload.items().get(0);
            SelectInventoryResponse inventoryResponse =
                    inventoryGrpcClient.selectInventory(Long.parseLong(item.productId()), item.quantity());

            var inventory = inventoryResponse.getInventory();
            InventoryReservation reservationResult =
                    inventoryReservationGrpcClient.createReservation(
                            inventory.getInventoryId(),
                            inventory.getProductId(),
                            item.quantity(),
                            300 // TTL seconds
                    );

            if ("RESERVED".equals(reservationResult.getStatus())) {
                // =============================
                // ⑤ Stock movement creation
                // =============================
                stockMovementGrpcClient.addStockMovement(
                        AddStockMovementRequest.newBuilder()
                                .setInventoryId(inventory.getInventoryId())
                                .setQuantity(item.quantity())
                                .setReferenceId("REF-" + inventory.getInventoryId())
                                .build());

                log.info("✅ Inventory reserved successfully: reservationId={}",
                        reservationResult.getReservationId());

            // =============================
            // ⑥ Warehouse & PurchaseOrder info (for auditing)
            // =============================
            warehouseGrpcClient.getWarehouse(inventory.getWarehouseId());

            // 복합키로 PurchaseOrder 조회
            GetPurchaseOrderRequest request = GetPurchaseOrderRequest.newBuilder()
                    .setKey(
                        PurchaseOrderKey.newBuilder()
                            .setSupplierId(1L) // TODO: inventory.getSupplierId()
                            .setWarehouseId(inventory.getWarehouseId())
                            .build()
                    )
                    .build();

            GetPurchaseOrderResponse poResponse = purchaseOrderGrpcClient.getPurchaseOrder(request);

            log.info("📦 PurchaseOrder confirmed (by composite key): supplierId={}, warehouseId={}, status={}",
                    1L,
                    inventory.getWarehouseId(),
                    poResponse.getPurchaseOrder().getStatus());

            // =============================
            // ⑦ TODO: Publish OrderShippedEvent (future)
            // =============================
            // orderEventPublisher.publish(new OrderShippedEvent(orderId, ...));
            } else {
                log.warn("⚠️ Inventory reservation failed: status={}", reservationResult.getStatus());
                // orderEventPublisher.publish(new InventoryReserveFailedEvent(orderId));
            }
        } catch (Exception e) {
            log.error("❌ Failed to handle order event", e);
            // orderEventPublisher.publish(new InventoryReserveFailedEvent(-1)); // fallback
        }
    }
}

