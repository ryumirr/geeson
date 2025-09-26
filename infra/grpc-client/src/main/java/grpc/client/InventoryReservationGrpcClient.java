package grpc.client;

import grpc.inventory.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import grpc.inventory.InventoryReservationServiceGrpc;
import grpc.inventory.InventoryReservationServiceGrpc.InventoryReservationServiceBlockingStub;

@Service
@Slf4j
public class InventoryReservationGrpcClient {

    private ManagedChannel channel;
    private InventoryReservationServiceGrpc.InventoryReservationServiceBlockingStub reservationStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565)
                .usePlaintext()
                .build();

        this.reservationStub = InventoryReservationServiceGrpc.newBlockingStub(channel);
    }

    /**
     * 예약 생성
     */
    public InventoryReservation createReservation(long inventoryId, long orderId, int reservedQuantity,
            int ttlSeconds) {
        CreateReservationRequest request = CreateReservationRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setOrderId(orderId)
                .setReservedQuantity(reservedQuantity)
                .setTtlSeconds(ttlSeconds)
                .build();

        try {
            CreateReservationResponse response = reservationStub.createReservation(request);
            return response.getReservation();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC createReservation failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 예약 단건 조회
     */
    public InventoryReservation getReservation(long reservationId) {
        GetReservationRequest request = GetReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .build();

        try {
            GetReservationResponse response = reservationStub.getReservation(request);
            return response.getReservation();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC getReservation failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 특정 주문의 예약 전체 조회
     */
    public java.util.List<InventoryReservation> getReservationsByOrder(long orderId) {
        GetReservationsByOrderRequest request = GetReservationsByOrderRequest.newBuilder()
                .setOrderId(orderId)
                .build();

        try {
            GetReservationsByOrderResponse response = reservationStub.getReservationsByOrder(request);
            return response.getReservationsList();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC getReservationsByOrder failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 특정 재고(inventory)의 예약 전체 조회
     */
    public java.util.List<InventoryReservation> getReservationsByInventory(long inventoryId) {
        GetReservationsByInventoryRequest request = GetReservationsByInventoryRequest.newBuilder()
                .setInventoryId(inventoryId)
                .build();

        try {
            GetReservationsByInventoryResponse response = reservationStub.getReservationsByInventory(request);
            return response.getReservationsList();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC getReservationsByInventory failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 예약 취소
     */
    public boolean cancelReservation(long reservationId) {
        CancelReservationRequest request = CancelReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .build();

        try {
            CancelReservationResponse response = reservationStub.cancelReservation(request);
            return response.getSuccess();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC cancelReservation failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 예약 확정 (출고 시점)
     */
    public boolean completeReservation(long reservationId) {
        CompleteReservationRequest request = CompleteReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .build();

        try {
            CompleteReservationResponse response = reservationStub.completeReservation(request);
            return response.getSuccess();
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC completeReservation failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 예약 단건 조회 (Optional 버전)
     */
    public java.util.Optional<InventoryReservation> findReservation(long reservationId) {
        GetReservationRequest request = GetReservationRequest.newBuilder()
                .setReservationId(reservationId)
                .build();

        try {
            GetReservationResponse response = reservationStub.getReservation(request);
            return java.util.Optional.of(response.getReservation());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                return java.util.Optional.empty();
            }
            log.error("❌ gRPC findReservation failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
        }
    }
}
