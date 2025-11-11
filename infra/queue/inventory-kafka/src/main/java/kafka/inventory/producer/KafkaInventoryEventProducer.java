package kafka.inventory.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.message.*;
import support.messaging.command.StockOutCreatedPayload;
import support.messaging.command.ShipmentReadyPayload;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaInventoryEventProducer implements InventoryEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Override
    public void publishStockOutCreatedEvent(StockOutCreatedPayload event) {
        try {
            String json = mapper.writeValueAsString(event);
            kafkaTemplate.send("stock-out-created-topic", String.valueOf(event.getInventoryId()), json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.err.println("[Kafka] Failed to publish event: " + ex.getMessage());
                        } else {
                            System.out.println("[Kafka] Published event: " + event);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize StockOutCreatedPayload", e);
        }
    }

    @Override
    public void publishShipmentReady(ShipmentReadyPayload event) {
        try {
            String json = mapper.writeValueAsString(event);
            kafkaTemplate.send("ord-ord-ship-succ-event", String.valueOf(event.orderId()), json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.err.println("[Kafka] Failed to publish event: " + ex.getMessage());
                        } else {
                            System.out.println("[Kafka] Published event: " + event);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize ShipmentReadyPayload", e);
        }
    }
}
