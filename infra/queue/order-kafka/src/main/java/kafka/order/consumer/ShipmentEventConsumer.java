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

import grpc.client.ShipmentGrpcClient; 
import grpc.shipment.GetShipmentResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentEventConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ShipmentGrpcClient shipmentGrpcClient;

    @KafkaListener(topics = "ord-ord-req-succ-event", groupId = "shipment-group")
    public void handleOrderCreated(String message) {
        try {
            OrderStartPayload payload = objectMapper.readValue(message, OrderStartPayload.class);

            log.info("Shipment received order event: orderId={}, totalPrice={}, items={}", 
                     payload.orderId(), payload.totalPrice(), payload.items());

            shipmentGrpcClient.createShipment(payload.orderId(), "TRACKING-NUMBER-" + payload.orderId());

        } catch (Exception e) {
            log.error("❌ Failed to parse order event", e);
        }
    }
}
