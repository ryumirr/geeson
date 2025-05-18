package app.consumer;

import app.inventory.service.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FooConsumer {
    private final FooService fooService;

    @KafkaListener(topics = "foo")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
