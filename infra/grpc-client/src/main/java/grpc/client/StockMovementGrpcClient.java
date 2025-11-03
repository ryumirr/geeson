package grpc.client;

import grpc.inventory.AddStockMovementRequest;
import grpc.inventory.AddStockMovementResponse;
import grpc.inventory.GetStockMovementsByInventoryRequest;
import grpc.inventory.GetStockMovementsByInventoryResponse;
import grpc.inventory.GetStockMovementByReferenceRequest;
import grpc.inventory.GetStockMovementByReferenceResponse;
import grpc.inventory.StockMovementServiceGrpc;
import grpc.inventory.StockMovementServiceGrpc.StockMovementServiceBlockingStub;
import support.messaging.command.StockOutCreatedPayload;

import domain.inventory.domain.message.StockOutCreatedEventPublisher;
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
    private StockOutCreatedEventPublisher stockOutCreatedEventPublisher;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565)
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
            AddStockMovementResponse response = stockMovementStub.addStockMovement(request);
            // test
            stockOutCreatedEventPublisher.publishStockOutCreatedEvent(
                    new StockOutCreatedPayload(
                            response.getStockMovement().getMovementId(), // ✅ nested getter
                            request.getInventoryId(),
                            "OUT",
                            request.getQuantity(),
                            request.getReferenceId(),
                            request.getDescription()
                    )
            );
            return response;
        } catch (StatusRuntimeException e) {
            log.error("gRPC call addStockMovement failed: {}", e.getStatus(), e);
            throw e;
        }
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
