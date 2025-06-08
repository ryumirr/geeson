package storage.rdb.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {"storage.rdb.order", "app.order", "domain.order"}
)
public class OrderDbMain {
}
