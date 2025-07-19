package storage.rdb.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"storage.rdb.product", "domain.product", "app.product"}
)
public class ProductDbMain {
}