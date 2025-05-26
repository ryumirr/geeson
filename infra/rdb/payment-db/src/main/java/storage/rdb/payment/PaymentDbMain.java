package storage.rdb.payment;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"storage.rdb.payment", "domain.payment", "app.payment"}
)
public class PaymentDbMain {
}
