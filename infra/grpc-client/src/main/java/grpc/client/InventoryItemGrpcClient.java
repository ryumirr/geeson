package grpc.client;

import grpc.inventory.CreateInventoryItemRequest;
import grpc.inventory.GetInventoryItemRequest;
import grpc.inventory.InventoryItemResponse;
import grpc.inventory.InventoryItemServiceGrpc;
import grpc.inventory.InventoryItemServiceGrpc.InventoryItemServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class InventoryItemGrpcClient {

    private ManagedChannel channel;
    private InventoryItemServiceBlockingStub inventoryItemStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565)  // docker-compose alias 또는 inventory-api
                .usePlaintext()
                .build();

        this.inventoryItemStub = InventoryItemServiceGrpc.newBlockingStub(channel);
    }

    /** gRPC 서버에서 단일 InventoryItem 조회 */
    public InventoryItemResponse getInventoryItem(Long inventoryItemId) {
        GetInventoryItemRequest request = GetInventoryItemRequest.newBuilder()
                .setInventoryItemId(inventoryItemId)
                .build();

        try {
            return inventoryItemStub.getInventoryItem(request);
        } catch (StatusRuntimeException e) {
            System.err.println("❌ gRPC getInventoryItem failed: " + e.getStatus());
            throw e;
        }
    }

    /** gRPC 서버에 새로운 InventoryItem 생성 요청 */
    public InventoryItemResponse createInventoryItem(Long inventoryId, String serialNumber, String status) {
        CreateInventoryItemRequest request = CreateInventoryItemRequest.newBuilder()
                .setInventoryId(inventoryId)
                .setSerialNumber(serialNumber != null ? serialNumber : "")
                .setStatus(status != null ? status : "AVAILABLE")
                .build();

        try {
            return inventoryItemStub.createInventoryItem(request);
        } catch (StatusRuntimeException e) {
            System.err.println("❌ gRPC createInventoryItem failed: " + e.getStatus());
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
