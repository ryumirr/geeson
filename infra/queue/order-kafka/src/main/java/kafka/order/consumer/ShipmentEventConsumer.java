package kafka.order.consumer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import support.uuid.UuidGenerator;

import support.messaging.command.InventoryReservePayload;
import support.messaging.command.OrderStartPayload;
import support.messaging.event.InventoryReserveFailedEvent;
import support.messaging.event.InventoryReserveSucceedEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.order.app.ShipmentApp;
import domain.order.entity.ShipmentJpaEntity;
import domain.order.message.OrderEventPublisher;
import grpc.client.ShipmentGrpcClient;
import grpc.client.InventoryReservationGrpcClient;
import grpc.client.StockMovementGrpcClient;
import grpc.client.InventoryGrpcClient;
import grpc.shipment.GetShipmentResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ShipmentGrpcClient shipmentGrpcClient;
    private final InventoryReservationGrpcClient inventoryReservationGrpcClient;
    private final StockMovementGrpcClient stockMovementGrpcClient;
    private final InventoryReservation inventoryReservation;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final OrderEventPublisher orderEventPublisher;

    @KafkaListener(topics = "ord-ord-req-succ-event", groupId = "shipment-group")
    public void handleOrderCreated(String message) {
        try {
            OrderStartPayload payload = objectMapper.readValue(message, OrderStartPayload.class);

            log.info("Shipment received order event: orderId={}, totalPrice={}, items={}", 
                     payload.orderId(), payload.totalPrice(), payload.items());

            shipmentGrpcClient.createShipment(payload.orderId(), "TRACKING-NUMBER-" + payload.orderId());
            List<InventoryReservation> reservations = inventoryReservationGrpcClient
                    .getReservationsByOrder(Long.parseLong(payload.orderId()));
            if (reservations.get(0).getReservationId() != 0L) {
                log.info("✅ Inventory reservation succeeded: reservationId={}", reservations.get(0).getReservationId());
                return;
            }
            SelectInventoryResponse inventory = inventoryGrpcClient
                    .selectInventory(Long.parseLong(payload.items().get(0).productId()),
                            payload.items().get(0).quantity());
            InventoryReservation reservationResult = inventoryReservationGrpcClient.createReservation(
                    inventory.getInventory().getInventoryId(),
                    inventory.getInventory().getProductId(),
                    payload.items().get(0).quantity(),
                    300 // TtlSeconds
            );
            // 예약 성공
            if (reservationResult.getStatus() == "RESERVED") {
                // 출고 요청
                stockMovementGrpcClient.addStockMovement(
                        grpc.inventory.AddStockMovementRequest.newBuilder()
                                .setInventoryId(inventory.getInventory().getInventoryId())
                                .setQuantity(payload.items().get(0).quantity())
                                .setReferenceId(String.valueOf(
                                        inventory.getInventory().getInventoryId() * hashCode()))
                                .build());
                log.info("✅ Inventory reservation succeeded: reservationId={}", reservationResult.getReservationId());

                // 창고 정보 확인(warehouses)
            }

        } catch (Exception e) {
            log.error("❌ Failed to parse order event", e);
        }
    }
}
