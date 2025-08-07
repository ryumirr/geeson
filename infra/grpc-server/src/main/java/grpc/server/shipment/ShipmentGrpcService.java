package grpc.server.shipment;

import grpc.shipment.CreateShipmentRequest;
import grpc.shipment.CreateShipmentResponse;
import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import app.order.port.in.CreateShipmentUseCase;
import app.order.port.in.GetShipmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@GrpcService
public class ShipmentGrpcService extends ShipmentServiceGrpc.ShipmentServiceImplBase {

        private final CreateShipmentUseCase createShipmentUseCase;
        private final GetShipmentUseCase getShipmentUseCase;

        @Override
        public void createShipment(CreateShipmentRequest request,
                        StreamObserver<CreateShipmentResponse> responseObserver) {
                var result = createShipmentUseCase.createShipment(
                                new CreateShipmentUseCase.CreateShipmentCommand(request.getOrderId(),
                                                request.getTrackingNumber()));

                var response = CreateShipmentResponse.newBuilder()
                                .setShipmentId(result.shipmentId())
                                .setOrderId(result.orderId())
                                .setTrackingNumber(result.trackingNumber())
                                .setStatus(result.status())
                                .setShippedDate(result.shippedDate())
                                .setCreatedAt(result.createdAt())
                                .setUpdatedAt(result.updatedAt())
                                .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
        }

        @Override
        public void getShipment(GetShipmentRequest request, StreamObserver<GetShipmentResponse> responseObserver) {
                var result = getShipmentUseCase
                                .getShipmentById(new GetShipmentUseCase.GetShipmentCommand(request.getShipmentId()));

                var builder = GetShipmentResponse.newBuilder()
                                .setShipmentId(result.shipmentId())
                                .setOrderId(result.orderId())
                                .setTrackingNumber(result.trackingNumber())
                                .setStatus(result.status());

                if (result.shippedDate() != null) {
                        builder.setShippedDate(result.shippedDate().toString());
                }

                if (result.deliveredDate() != null) {
                        builder.setDeliveredDate(result.deliveredDate().toString());
                }

                if (result.createdAt() != null) {
                        builder.setCreatedAt(result.createdAt().toString());
                }

                if (result.updatedAt() != null) {
                        builder.setUpdatedAt(result.updatedAt().toString());
                }

                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
        }
}
