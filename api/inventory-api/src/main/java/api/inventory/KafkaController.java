package api.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import queue.kafka.producer.KafkaProducerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        kafkaProducerService.send("test.topic", message);
        return "ok";
    }
}
