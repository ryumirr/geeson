package grpc.client;

import grpc.warehouse.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WarehouseGrpcClient {

    private ManagedChannel channel;
    private WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("inventory-api", 6566)
                .usePlaintext()
                .build();

        this.warehouseStub = WarehouseServiceGrpc.newBlockingStub(channel);
        log.info("WarehouseGrpcClient initialized");
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            log.info("WarehouseGrpcClient channel shutdown");
        }
    }

    // ===== Create Warehouse =====
    public CreateWarehouseResponse createWarehouse(String name, String location, int capacity) {
        try {
            CreateWarehouseRequest request = CreateWarehouseRequest.newBuilder()
                    .setName(name)
                    .setLocation(location)
                    .setCapacity(capacity)
                    .build();
            return warehouseStub.createWarehouse(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call createWarehouse failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Get Warehouse =====
    public GetWarehouseResponse getWarehouse(long warehouseId) {
        try {
            GetWarehouseRequest request = GetWarehouseRequest.newBuilder()
                    .setWarehouseId(warehouseId)
                    .build();
            return warehouseStub.getWarehouse(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call getWarehouse failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Update Warehouse =====
    public UpdateWarehouseResponse updateWarehouse(long warehouseId, String name, String location, int capacity) {
        try {
            UpdateWarehouseRequest request = UpdateWarehouseRequest.newBuilder()
                    .setWarehouseId(warehouseId)
                    .setName(name)
                    .setLocation(location)
                    .setCapacity(capacity)
                    .build();
            return warehouseStub.updateWarehouse(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call updateWarehouse failed: {}", e.getStatus(), e);
            throw e;
        }
    }
}

