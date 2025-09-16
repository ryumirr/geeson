package grpc.server.order;

import grpc.shipment.CreateShipmentRequest;
import grpc.shipment.CreateShipmentResponse;
import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import app.order.port.in.CreateShipmentUseCase;
import app.order.port.in.GetShipmentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@GrpcService
public class ShipmentGrpcService extends ShipmentServiceGrpc.ShipmentServiceImplBase {

    private final CreateShipmentUseCase createShipmentUseCase;
    private final GetShipmentUseCase getShipmentUseCase;

    @Override
    public void createShipment(CreateShipmentRequest request,
            StreamObserver<CreateShipmentResponse> responseObserver) {
        try {
            var result = createShipmentUseCase.createShipment(
                    new CreateShipmentUseCase.CreateShipmentCommand(
                            request.getOrderId(),
                            request.getTrackingNumber()));

            var response = CreateShipmentResponse.newBuilder()
                    .setShipmentId(result.shipmentId())
                    .setOrderId(result.orderId())
                    .setTrackingNumber(result.trackingNumber())
                    .setStatus(result.status())
                    .setShippedDate(result.shippedDate())
                    .setDeliveredDate(result.deliveredDate())
                    .setCreatedAt(result.createdAt())
                    .setUpdatedAt(result.updatedAt())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .withCause(e)
                            .asRuntimeException());
        } catch (Exception e) {
            // log.error("❌ getShipment failed", e); // 반드시 로그 남기기
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Unexpected server error: " + e.getMessage())
                            .withCause(e)
                            .asRuntimeException());
        }
    }

    @Override
    public void getShipment(GetShipmentRequest request,
            StreamObserver<GetShipmentResponse> responseObserver) {
        try {
            var result = getShipmentUseCase.getShipmentById(
                    new GetShipmentUseCase.GetShipmentCommand(request.getShipmentId()));

            // null-safe 변환
            GetShipmentResponse response = GetShipmentResponse.newBuilder()
                    .setShipmentId(result.shipmentId())
                    .setOrderId(result.orderId())
                    .setTrackingNumber(safe(result.trackingNumber()))
                    .setStatus(safe(result.status()))
                    .setShippedDate(safe(result.shippedDate()))
                    .setDeliveredDate(safe(result.deliveredDate()))
                    .setCreatedAt(safe(result.createdAt()))
                    .setUpdatedAt(safe(result.updatedAt()))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Unexpected server error").asRuntimeException());
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}