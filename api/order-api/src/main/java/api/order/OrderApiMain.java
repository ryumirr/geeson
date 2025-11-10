package api.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(
  scanBasePackages = {
    "api.order",
    "app.order",
    "domain.order",
    "storage.rdb.order",
    "kafka.order",              
    "infra.queue.order",        
    "client",
    "grpc.client"
  }
)
public class OrderApiMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderApiMain.class, args);
    }
}

