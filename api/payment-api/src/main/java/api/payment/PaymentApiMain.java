package api.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(
    scanBasePackages = {"api.payment", "app.payment", "domain.payment", "storage.rdb.payment",  "kafka.payment", "support.uuid", "client.pgClient"}
)
public class PaymentApiMain {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApiMain.class, args);
    }
}
