package app.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"app.product", "domain.product", "support.uuid"}
)
public class ProductAppMain {
}
