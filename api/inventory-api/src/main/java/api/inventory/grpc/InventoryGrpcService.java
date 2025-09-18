package api.inventory.grpc;

import app.inventory.app.InventoryAddApp;
import app.inventory.app.InventorySelectApp;
import domain.inventory.domain.entity.InventoryJpaEntity;
import grpc.inventory.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryAddApp inventoryAddApp;
    private final InventorySelectApp inventorySelectApp;

    /**
     * gRPC - AddInventory
     */
    @Override
    public void addInventory(AddInventoryRequest request,
                             StreamObserver<AddInventoryResponse> responseObserver) {
        try {
            InventoryJpaEntity entity = inventoryAddApp.addInventory(
                    request.getProductId(),
                    request.getWarehouseId(),
                    request.getTotalQuantity(),
                    request.getReorderLevel(),
                    request.getReorderQuantity()
            );

            Inventory inventory = Inventory.newBuilder()
                    .setInventoryId(entity.getInventoryId())
                    .setProductId(entity.getProduct().getProductId())
                    .setWarehouseId(entity.getWareHouse().getWarehouseId())
                    .setTotalQuantity(entity.getTotalQuantity())
                    .setReservedQuantity(entity.getReservedQuantity())
                    .setAvailableQuantity(entity.getAvailableQuantity())
                    .setReorderLevel(entity.getReorderLevel())
                    .setReorderQuantity(entity.getReorderQuantity())
                    .setCreatedAt(entity.getCreatedAt().toString())
                    .setUpdatedAt(entity.getUpdatedAt().toString())
                    .build();

            responseObserver.onNext(AddInventoryResponse.newBuilder().setInventory(inventory).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("Error adding inventory").asRuntimeException());
        }
    }

    /**
     * gRPC - SelectInventory
     */
    @Override
    public void selectInventory(SelectInventoryRequest request,
                                StreamObserver<SelectInventoryResponse> responseObserver) {
        try {
            InventoryJpaEntity entity = inventorySelectApp.findAvailableInventory(
                    request.getProductId(),
                    request.getQuantity()
            );

            Inventory inventory = Inventory.newBuilder()
                    .setInventoryId(entity.getInventoryId())
                    .setProductId(entity.getProduct().getProductId())
                    .setWarehouseId(entity.getWareHouse().getWarehouseId())
                    .setTotalQuantity(entity.getTotalQuantity())
                    .setReservedQuantity(entity.getReservedQuantity())
                    .setAvailableQuantity(entity.getAvailableQuantity())
                    .setReorderLevel(entity.getReorderLevel())
                    .setReorderQuantity(entity.getReorderQuantity())
                    .setCreatedAt(entity.getCreatedAt().toString())
                    .setUpdatedAt(entity.getUpdatedAt().toString())
                    .build();

            responseObserver.onNext(SelectInventoryResponse.newBuilder().setInventory(inventory).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Inventory not found").asRuntimeException());
        }
    }
}
