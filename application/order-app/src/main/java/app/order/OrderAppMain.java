package app.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"app.order", "app.order.port.in", "domain.order", "support.uuid", "storage.rdb.order"}
)
public class OrderAppMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderAppMain.class, args);
    }
}
