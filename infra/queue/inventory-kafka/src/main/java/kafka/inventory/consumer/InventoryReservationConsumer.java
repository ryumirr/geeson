package kafka.inventory.consumer;

import app.inventory.app.InventoryReservationApp;
import app.inventory.command.InventoryReservationCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import support.messaging.command.InventoryReservePayload;
import support.messaging.event.InventoryReserveFailedEvent;
import support.messaging.event.InventoryReserveSucceedEvent;
import support.uuid.UuidGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryReservationConsumer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UuidGenerator uuidGenerator;
    private final InventoryReservationApp inventoryReservationApp;

    @KafkaListener(topics = "ord-inv-dec-cmd", groupId = "inventory-consumer-group")
    public void inventoryDecreaseCommand(String command){
        log.info("inventory decrease command received: {}", command);
        InventoryReservePayload payload = null;
        try {
            payload = mapper.readValue(command, InventoryReservePayload.class);
        } catch (JsonProcessingException e) {
            log.error("failed to parse command: {}", e.getMessage());
        }

        try {
            InventoryReservationJpaEntity reserved = inventoryReservationApp.reserveInventory(new InventoryReservationCommand(
                Long.valueOf(payload.getReservationId()),
                Long.parseLong(payload.getProductId()),
                Long.parseLong(payload.getOrderId()),
                payload.getQuantity()
            ));

            kafkaTemplate.send("ord-inv-dec-succ-evt", mapper.writeValueAsString(new InventoryReserveSucceedEvent(
                String.valueOf(uuidGenerator.nextId()),
                payload.getSagaId(),
                payload.getStepId(),
                payload.getOrderId(),
                payload.getProductId(),
                String.valueOf(reserved.getReservationId()),
                "SUCCESS"
            )));
        } catch (Exception e) {
            log.error("failed to reserve inventory: {}", e.getMessage());
            try {
                kafkaTemplate.send("ord-inv-dec-fail-evt", mapper.writeValueAsString(new InventoryReserveFailedEvent(
                    String.valueOf(uuidGenerator.nextId()),
                    payload.getSagaId(),
                    payload.getStepId(),
                    payload.getOrderId(),
                    payload.getProductId(),
                    e.getMessage()
                )));
            } catch (JsonProcessingException ex) {
                log.error("failed to send failed event: {}", ex.getMessage());
            }
        }

    }
}
