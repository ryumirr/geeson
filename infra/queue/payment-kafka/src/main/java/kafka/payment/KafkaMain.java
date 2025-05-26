package kafka.payment;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"kafka.payment", "app.payment", "domain.payment"}
)
public class KafkaMain {
}
