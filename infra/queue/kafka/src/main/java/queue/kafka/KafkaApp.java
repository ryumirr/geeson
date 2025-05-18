package queue.kafka;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(
        scanBasePackages = {"queue.kafka", "app.order"}
)
public class KafkaApp {
    public static void main(String[] args) {
        run(KafkaApp.class, args);
    }
}
