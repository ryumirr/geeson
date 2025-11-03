package kafka.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"kafka.order", "app.order", "domain.order", "grpc.shipment"}
)
public class KafkaMain {
}
