package storage.rdb.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "storage.rdb.order", "app.order", "domain.order", "domain.order.repository", "storage.rdb.order.repository.adapter",
})
@EnableJpaRepositories(basePackages = {"domain.order.repository", "storage.rdb.order.repository.adapter"})
@EntityScan(basePackages = "domain.order.entity")
public class OrderDbMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderDbMain.class, args);
    }
}
