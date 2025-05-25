package kafka.order.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.order.domain.message.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import support.messaging.OrderCreatedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventProducer implements OrderEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        String topic = "order.created";
        try {
            kafkaTemplate.send(topic, String.valueOf(event.orderId()), mapper.writeValueAsString(event)).whenComplete((recordMetadata, ex) -> {
                log.info("published message to topic: {}, offset: {}, partition: {}", topic, recordMetadata.getRecordMetadata().offset(), recordMetadata.getRecordMetadata().partition());
                if(ex != null) {
                    log.error("failed to publish message to topic: {}", topic, ex);
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
