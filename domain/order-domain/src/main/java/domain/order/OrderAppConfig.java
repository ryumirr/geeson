package domain.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(
        scanBasePackages = {"domain.order"}
)
public class OrderAppConfig {
    public static void main(String[] args) {
        run(OrderAppConfig.class, args);
    }
}
