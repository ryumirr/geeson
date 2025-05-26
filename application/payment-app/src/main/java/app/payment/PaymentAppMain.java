package app.payment;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"app.payment", "domain.payment"}
)
public class PaymentAppMain {
}
