package domain.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = {"domain.product"})
public class ProductApplication {
    public static void main(String[] args) {
        run(ProductApplication.class, args);
    }
}
