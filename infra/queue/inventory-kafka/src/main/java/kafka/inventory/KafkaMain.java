package kafka.inventory;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"kafka.inventory", "app.inventory", "domain.inventory"}
)
public class KafkaMain {
}
