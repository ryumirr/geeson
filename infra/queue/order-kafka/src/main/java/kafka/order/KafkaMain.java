package kafka.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
    scanBasePackages = {"kafka.order", "app.order", "domain.order", "infra.queue.order", "grpc.shipment"}
)
@EnableKafka
public class KafkaMain {
}
