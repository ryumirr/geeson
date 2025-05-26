package app.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"app.order", "domain.order"}
)
public class OrderAppMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderAppMain.class, args);
    }
}
