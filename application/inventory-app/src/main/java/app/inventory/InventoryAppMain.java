package app.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        // , "app.inventory.port.in"
        scanBasePackages = {
                "app.inventory",
                "domain.inventory",
                "support.uuid",
                "storage.rdb.inventory",
                "grpc.client",
                "grpc.shipment" })
public class InventoryAppMain {
    public static void main(String[] args) {
        SpringApplication.run(InventoryAppMain.class, args);
    }
}
