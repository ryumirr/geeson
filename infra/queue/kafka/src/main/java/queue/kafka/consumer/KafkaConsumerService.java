package queue.kafka.consumer;

import app.order.FooAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final FooAppService fooService;

    @KafkaListener(topics = "test.topic", groupId = "test-group")
    public void listen(String message) {
        fooService.listen(message);
    }
}
