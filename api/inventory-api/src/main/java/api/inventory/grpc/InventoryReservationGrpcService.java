package api.inventory.grpc;

import app.inventory.app.IdempotencyService;
import app.inventory.app.InventoryReservationApp;
import app.inventory.command.InventoryReservationCommand;
import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import domain.inventory.exception.NotEnoughInventoryException;
import grpc.inventory.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import support.uuid.UuidGenerator;

@RequiredArgsConstructor
@GrpcService
public class InventoryReservationGrpcService extends InventoryReservationServiceGrpc.InventoryReservationServiceImplBase {

    private final InventoryReservationApp reservationApp;
    private final UuidGenerator uuidGenerator;
    private final IdempotencyService idempotencyService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void createReservation(CreateReservationRequest request,
                                  StreamObserver<CreateReservationResponse> responseObserver) {
        String idempotencyKey = "RESERVATION-ORDER-" + request.getOrderId();
        try {
            if (idempotencyService.isAlreadyProcessed(idempotencyKey)) {
                var existing = reservationApp.getByOrderId(request.getOrderId());
                if (!existing.isEmpty()) {
                    responseObserver.onNext(CreateReservationResponse.newBuilder()
                        .setReservation(toProto(existing.get(0)))
                        .build());
                    responseObserver.onCompleted();
                    return;
                }
            }

            idempotencyService.markAsProcessing(idempotencyKey, "grpc-createReservation");

            Integer ttlSeconds = request.getTtlSeconds() > 0 ? request.getTtlSeconds() : null;

            InventoryReservationJpaEntity entity = reservationApp.reserveInventory(
                new InventoryReservationCommand(
                    uuidGenerator.nextId(),
                    request.getInventoryId(),
                    request.getOrderId(),
                    request.getReservedQuantity(),
                    ttlSeconds
                )
            );

            idempotencyService.markAsCompleted(idempotencyKey);

            CreateReservationResponse response = CreateReservationResponse.newBuilder()
                .setReservation(toProto(entity))
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NotEnoughInventoryException e) {
            idempotencyService.markAsFailed(idempotencyKey, e.getMessage());
            responseObserver.onError(Status.FAILED_PRECONDITION
                .withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            idempotencyService.markAsFailed(idempotencyKey, e.getMessage());
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to create reservation").asRuntimeException());
        }
    }

    @Override
    public void getReservation(GetReservationRequest request,
                               StreamObserver<GetReservationResponse> responseObserver) {
        try {
            InventoryReservationJpaEntity entity = reservationApp.getById(request.getReservationId());
            GetReservationResponse response = GetReservationResponse.newBuilder()
                .setReservation(toProto(entity))
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.NOT_FOUND
                .withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getReservationsByOrder(GetReservationsByOrderRequest request,
                                       StreamObserver<GetReservationsByOrderResponse> responseObserver) {
        try {
            var reservations = reservationApp.getByOrderId(request.getOrderId())
                .stream()
                .map(this::toProto)
                .collect(Collectors.toList());

            GetReservationsByOrderResponse response = GetReservationsByOrderResponse.newBuilder()
                .addAllReservations(reservations)
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to get reservations by order").asRuntimeException());
        }
    }

    @Override
    public void getReservationsByInventory(GetReservationsByInventoryRequest request,
                                           StreamObserver<GetReservationsByInventoryResponse> responseObserver) {
        try {
            var reservations = reservationApp.getByInventoryId(request.getInventoryId(), null)
                .stream()
                .map(this::toProto)
                .collect(Collectors.toList());

            GetReservationsByInventoryResponse response = GetReservationsByInventoryResponse.newBuilder()
                .addAllReservations(reservations)
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to get reservations by inventory").asRuntimeException());
        }
    }

    @Override
    public void cancelReservation(CancelReservationRequest request,
                                  StreamObserver<CancelReservationResponse> responseObserver) {
        try {
            reservationApp.updateStatus(request.getReservationId(),
                module.enums.ReservationStatus.CANCELLED);
            InventoryReservationJpaEntity entity = reservationApp.getById(request.getReservationId());

            CancelReservationResponse response = CancelReservationResponse.newBuilder()
                .setSuccess(true)
                .setReservation(toProto(entity))
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to cancel reservation").asRuntimeException());
        }
    }

    @Override
    public void completeReservation(CompleteReservationRequest request,
                                    StreamObserver<CompleteReservationResponse> responseObserver) {
        try {
            reservationApp.updateStatus(request.getReservationId(),
                module.enums.ReservationStatus.COMPLETED);
            InventoryReservationJpaEntity entity = reservationApp.getById(request.getReservationId());

            CompleteReservationResponse response = CompleteReservationResponse.newBuilder()
                .setSuccess(true)
                .setReservation(toProto(entity))
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                .withDescription("Failed to complete reservation").asRuntimeException());
        }
    }

    // ===== Helper: Entity → gRPC 변환 =====
    private InventoryReservation toProto(InventoryReservationJpaEntity entity) {
        return InventoryReservation.newBuilder()
            .setReservationId(entity.getReservationId())
            .setInventoryId(entity.getInventory().getInventoryId())
            .setOrderId(entity.getOrderId())
            .setReservedQuantity(entity.getReservedQuantity())
            .setReservedAt(entity.getReservedAt().format(FORMATTER))
            .setExpiresAt(entity.getExpiresAt() != null ? entity.getExpiresAt().format(FORMATTER) : "")
            .setStatus(entity.getStatus().name())
            .build();
    }
}
