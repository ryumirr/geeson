package app.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(
        scanBasePackages = {"app.order"}
)
public class OrderAppConfig {
    public static void main(String[] args) {
        run(OrderAppConfig.class, args);
    }
}
