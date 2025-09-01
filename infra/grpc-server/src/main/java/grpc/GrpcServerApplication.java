package grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
    scanBasePackages = {
        "grpc",           // proto, gRPC 서버
        "app.order",      // 애플리케이션/도메인 서비스 (Order)
        "app.inventory",  // (있다면) 애플리케이션/도메인 서비스 (Inventory)
        "support.uuid"
    }
)
// 자동등록 대상이 아닌 설정/서비스만 명시 임포트
@Import({
    // --- gRPC/infra configs ---
    grpc.server.config.UuidConfig.class,
    grpc.server.config.KafkaConfig.class,
    grpc.server.config.OrderEventPublisherConfig.class,
    grpc.server.shipment.ShipmentGrpcService.class,

    // --- 멀티 DB configs (핵심) ---
    grpc.server.config.OrderDbConfig.class,
    grpc.server.config.InventoryDbConfig.class
})
public class GrpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
        System.out.println("🚀 Running GrpcServerApplication!!");
    }
}
