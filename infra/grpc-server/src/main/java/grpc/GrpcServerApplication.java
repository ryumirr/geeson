package grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
    scanBasePackages = {
        "grpc",           // proto, gRPC 서버
        "app.order",
        "app.inventory",
        "support.uuid"
    }
)
// 자동등록 대상이 아닌 설정/서비스만 명시 임포트
@Import({
    // --- gRPC/infra configs ---
    grpc.server.config.UuidConfig.class,
    grpc.server.config.KafkaConfig.class,
    grpc.server.config.OrderEventPublisherConfig.class,
    grpc.server.order.ShipmentGrpcService.class,

    // --- 멀티 DB configs ---
    grpc.server.config.OrderDbConfig.class,
    grpc.server.config.InventoryDbConfig.class
})
public class GrpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
        System.out.println("🚀 Running GrpcServerApplication!!");
    }
}
