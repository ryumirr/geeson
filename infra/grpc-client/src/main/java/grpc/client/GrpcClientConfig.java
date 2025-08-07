package grpc.client;  

@Configuration
public class GrpcClientConfig {
    @GrpcClient("shipment")
    private ShipmentServiceGrpc.ShipmentServiceBlockingStub shipmentStub;
}
