package kafka.inventory.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.message.StockOutCreatedEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import support.messaging.command.OrderStartPayload;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaStockMovementEventProducer implements StockOutCreatedEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public void publishStockOutCreatedEvent(OrderStartPayload event) {
        String topic = "ord-stock-create-succ-event";
        try {
            kafkaTemplate.send(topic, String.valueOf(event.orderId()), mapper.writeValueAsString(event)).whenComplete((recordMetadata, ex) -> {
                log.info("published message to topic: {}, offset: {}, partition: {}", topic, recordMetadata.getRecordMetadata().offset(), recordMetadata.getRecordMetadata().partition());
                if(ex != null) {
                    log.error("failed to publish message to topic: {}", topic, ex);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
