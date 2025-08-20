package grpc.client;

import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc;
import grpc.shipment.ShipmentServiceGrpc.ShipmentServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ShipmentGrpcClient {

    private ShipmentServiceBlockingStub shipmentStub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565) // plaintext port
                .usePlaintext()                 // ⚠️ important to avoid TLS (443)
                .build();

        shipmentStub = ShipmentServiceGrpc.newBlockingStub(channel);
    }

    public GetShipmentResponse getShipment(Long shipmentId) {
        GetShipmentRequest request = GetShipmentRequest.newBuilder()
                .setShipmentId(shipmentId)
                .build();

        return shipmentStub.getShipment(request);
    }
}
