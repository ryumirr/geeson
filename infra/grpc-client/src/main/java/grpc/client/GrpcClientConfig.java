package grpc.client;  

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import grpc.shipment.ShipmentServiceGrpc;

@Configuration
public class GrpcClientConfig {
    @GrpcClient("shipment")
    private ShipmentServiceGrpc.ShipmentServiceBlockingStub shipmentStub;

    public ShipmentServiceGrpc.ShipmentServiceBlockingStub shipmentStub() {
        return shipmentStub;
    }
}
