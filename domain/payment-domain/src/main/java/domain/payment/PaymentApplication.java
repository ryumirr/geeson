package domain.payment;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = {"domain.payment"})
public class PaymentApplication {
    public static void main(String[] args) {
        run(PaymentApplication.class, args);
    }
}
