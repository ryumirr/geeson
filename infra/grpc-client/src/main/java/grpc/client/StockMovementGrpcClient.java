package grpc.client;

import grpc.inventory.AddStockMovementRequest;
import grpc.inventory.AddStockMovementResponse;
import grpc.inventory.GetStockMovementsByInventoryRequest;
import grpc.inventory.GetStockMovementsByInventoryResponse;
import grpc.inventory.GetStockMovementByReferenceRequest;
import grpc.inventory.GetStockMovementByReferenceResponse;
import grpc.inventory.MovementType;
import grpc.inventory.StockMovementServiceGrpc;
import grpc.inventory.StockMovementServiceGrpc.StockMovementServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockMovementGrpcClient {

    private ManagedChannel channel;
    private StockMovementServiceBlockingStub stockMovementStub;
   // private StockOutCreatedEventPublisher stockOutCreatedEventPublisher;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("inventory-api", 6566)
                .usePlaintext()
                .build();

        this.stockMovementStub = StockMovementServiceGrpc.newBlockingStub(channel);
        log.info("StockMovementGrpcClient initialized");
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            log.info("StockMovementGrpcClient channel shutdown");
        }
    }

    // ===== Add =====
    public AddStockMovementResponse addStockMovement(AddStockMovementRequest request) {
        try {
            return stockMovementStub.addStockMovement(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call addStockMovement failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    /**
     * 출고 흐름에서 내부 movementType 조립 책임을 감춘다.
     */
    public AddStockMovementResponse recordStockOut(long inventoryId, int quantity, String referenceId, String description) {
        AddStockMovementRequest request = AddStockMovementRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setMovementType(MovementType.OUT)
                .setQuantity(quantity)
                .setReferenceId(referenceId)
                .setDescription(description != null ? description : "")
                .build();
        return addStockMovement(request);
    }

    // ===== Get by Inventory =====
    public GetStockMovementsByInventoryResponse getStockMovementsByInventory(long inventoryId) {
        try {
            GetStockMovementsByInventoryRequest request =
                    GetStockMovementsByInventoryRequest.newBuilder()
                            .setInventoryId(inventoryId)
                            .build();
            return stockMovementStub.getStockMovementsByInventory(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call getStockMovementsByInventory failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Get by Reference =====
    public GetStockMovementByReferenceResponse getStockMovementByReference(Long referenceId) {
        try {
            GetStockMovementByReferenceRequest request =
                    GetStockMovementByReferenceRequest.newBuilder()
                            .setReferenceId(referenceId)
                            .build();
            return stockMovementStub.getStockMovementByReference(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call getStockMovementByReference failed: {}", e.getStatus(), e);
            throw e;
        }
    }
}
