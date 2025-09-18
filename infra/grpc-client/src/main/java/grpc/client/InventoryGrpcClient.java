package grpc.client;

import grpc.inventory.AddInventoryRequest;
import grpc.inventory.AddInventoryResponse;
import grpc.inventory.SelectInventoryRequest;
import grpc.inventory.SelectInventoryResponse;
import grpc.inventory.InventoryServiceGrpc;
import grpc.inventory.InventoryServiceGrpc.InventoryServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class InventoryGrpcClient {

    private ManagedChannel channel;
    private InventoryServiceBlockingStub inventoryStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565) // docker-compose service alias
                .usePlaintext()
                .build();

        this.inventoryStub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    /** gRPC 서버에 새로운 Inventory 추가 */
    public AddInventoryResponse addInventory(Long productId,
                                             Long warehouseId,
                                             int totalQuantity,
                                             int reorderLevel,
                                             int reorderQuantity) {
        AddInventoryRequest request = AddInventoryRequest.newBuilder()
                .setProductId(productId)
                .setWarehouseId(warehouseId)
                .setTotalQuantity(totalQuantity)
                .setReorderLevel(reorderLevel)
                .setReorderQuantity(reorderQuantity)
                .build();

        try {
            return inventoryStub.addInventory(request);
        } catch (StatusRuntimeException e) {
            System.err.println("❌ gRPC addInventory failed: " + e.getStatus());
            throw e;
        }
    }

    /** gRPC 서버에서 특정 상품의 사용 가능한 재고 조회 */
    public SelectInventoryResponse selectInventory(Long productId, int quantity) {
        SelectInventoryRequest request = SelectInventoryRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();

        try {
            return inventoryStub.selectInventory(request);
        } catch (StatusRuntimeException e) {
            System.err.println("❌ gRPC selectInventory failed: " + e.getStatus());
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
