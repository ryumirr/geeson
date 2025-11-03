package kafka.inventory.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.message.StockOutCreatedEventPublisher;
import support.messaging.command.StockOutCreatedPayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStockMovementEventProducer implements StockOutCreatedEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;
    private static final String TOPIC = "stock-out-created-topic";

    public KafkaStockMovementEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void publishStockOutCreatedEvent(StockOutCreatedPayload event) {
        try {
            String json = mapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, String.valueOf(event.getInventoryId()), json)
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
}
