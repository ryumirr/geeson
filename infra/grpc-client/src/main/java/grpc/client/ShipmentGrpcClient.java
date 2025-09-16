package grpc.client;

import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc;
import grpc.shipment.ShipmentServiceGrpc.ShipmentServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class ShipmentGrpcClient {

    private ManagedChannel channel;
    private ShipmentServiceBlockingStub shipmentStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("shipment", 6565)
                .usePlaintext()
                .build();

        this.shipmentStub = ShipmentServiceGrpc.newBlockingStub(channel);
    }

    public GetShipmentResponse getShipment(Long shipmentId) {
        GetShipmentRequest request = GetShipmentRequest.newBuilder()
                .setShipmentId(shipmentId)
                .build();

        try {
            return shipmentStub.getShipment(request);
        } catch (StatusRuntimeException e) {
            // gRPC 호출 실패 시 로그 남기기
            System.err.println("❌ gRPC getShipment failed: " + e.getStatus());
            throw e; // 혹은 Optional.empty() / custom exception
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}
