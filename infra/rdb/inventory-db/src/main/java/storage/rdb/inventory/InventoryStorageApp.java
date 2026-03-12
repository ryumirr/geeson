package storage.rdb.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {"storage.rdb.inventory", "app.inventory"}
)
@EntityScan(basePackages = {"app.inventory", "storage.rdb.inventory"})
public class InventoryStorageApp {
    public static void main(String[] args) {
        SpringApplication.run(InventoryStorageApp.class, args);
    }
}
