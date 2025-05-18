package queue.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import support.messaging.FooEvent;

@Component
@RequiredArgsConstructor
public class FooEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderCompletedEvent(FooEvent event) {
        kafkaTemplate.send("order.completed", "foo");
    }
}
