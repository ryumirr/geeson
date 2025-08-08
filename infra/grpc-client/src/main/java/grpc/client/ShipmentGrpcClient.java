package grpc.client;

import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc;
import grpc.shipment.ShipmentServiceGrpc.ShipmentServiceBlockingStub;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ShipmentGrpcClient {

    @GrpcClient("shipment")
    private ShipmentServiceBlockingStub shipmentStub;

    public GetShipmentResponse getShipment(Long shipmentId) {
        GetShipmentRequest request = GetShipmentRequest.newBuilder()
                .setShipmentId(shipmentId)
                .build();

        return shipmentStub.getShipment(request);
    }
}

