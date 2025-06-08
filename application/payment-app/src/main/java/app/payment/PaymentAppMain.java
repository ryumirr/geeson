package app.payment;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"app.payment", "domain.payment", "support.uuid"}
)
public class PaymentAppMain {
}
