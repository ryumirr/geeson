package api.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"api.order", "app.order", "domain.order", "storage.rdb.order", "kafka.order"}
)
public class OrderApiMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiMain.class, args);
    }
}
