package grpc.client.config;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import grpc.shipment.ShipmentServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
/**
 * @todo MockBean->Mock으로 변경 시, 이용예정
 */
public class TestGrpcClientConfiguration {

    @Bean
    public ManagedChannel shipmentGrpcChannel() {
        return InProcessChannelBuilder.forName("test-shipment-server").directExecutor().build();
    }

    @Bean
    public ShipmentServiceGrpc.ShipmentServiceBlockingStub shipmentGrpcClient(ManagedChannel shipmentGrpcChannel) {
        return ShipmentServiceGrpc.newBlockingStub(shipmentGrpcChannel);
    }
}
