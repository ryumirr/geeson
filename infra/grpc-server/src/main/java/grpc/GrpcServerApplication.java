package grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import app.order.app.ShipmentApp;
import app.order.app.CustomerRegisterApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
        "grpc", // proto 생성된 클래스 패키지
        "app.order",
})
// 자동등록 대상이 아닌 클래스들 수동 임포트
@Import({
        grpc.server.config.UuidConfig.class,
        grpc.server.config.KafkaConfig.class,
        grpc.server.config.OrderEventPublisherConfig.class,
        grpc.server.shipment.ShipmentGrpcService.class,
})
// 의존성 컴포넌트 필요한 부분만 등록, 필요시 화이트리스트형식으로 추가
@ComponentScan(basePackages = {
        "storage.rdb.order.repository",
        "app.order.port.in",
        "app.order.app",
})
@EnableJpaRepositories(basePackages = {
        "storage.rdb.order.repository.adapter" // SpringData JPA interface
})
@EntityScan(basePackages = {
        "domain.order.entity"
})
public class GrpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
        System.out.println("🚀 Running GrpcServerApplication!!");
        System.out.println(args);
    }

    /**
     * [삭제예정] grpc 컴파일된 클래스들 확인용
     * @param ctx
     * @return
     */
    @Bean
    CommandLineRunner runner(ApplicationContext ctx) {
        return args -> {
            System.out.println("========== Beans ==========");
            String[] beans = ctx.getBeanDefinitionNames();
            Arrays.sort(beans);
            for (String bean : beans) {
                // if (bean.toLowerCase().contains("shipment")) {
                System.out.println("💡 " + bean);
                // }
            }
        };
    }
}
