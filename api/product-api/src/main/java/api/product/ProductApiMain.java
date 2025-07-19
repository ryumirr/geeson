package api.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(
    scanBasePackages = {"api.product", "app.product", "domain.product", "storage.rdb.product"}
)
public class ProductApiMain {
    public static void main(String[] args) { run(ProductApiMain.class, args); }
}
