package api.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
    scanBasePackages = { "api.inventory", "app.inventory", "domain.inventory", "storage.rdb.inventory", "kafka.inventory"}
)
public class InventoryApiMain {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApiMain.class, args);
    }
}
