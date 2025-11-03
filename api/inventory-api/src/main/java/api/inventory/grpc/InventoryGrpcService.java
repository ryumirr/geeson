package api.inventory.grpc;

import java.util.stream.Collectors;

import app.inventory.app.InventoryAddApp;
import app.inventory.app.InventorySelectApp;
import app.inventory.dto.ReserveResultDto;
import domain.inventory.domain.entity.InventoryJpaEntity;
import grpc.inventory.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Map;

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
                                        request.getReorderQuantity());

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
                        responseObserver.onError(
                                        Status.INTERNAL.withDescription("Error adding inventory").asRuntimeException());
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
                                        request.getQuantity());

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
                        responseObserver.onError(
                                        Status.NOT_FOUND.withDescription("Inventory not found").asRuntimeException());
                }
        }

        @Override
        public void reserveInventory(ReserveInventoryRequest request,
                        StreamObserver<ReserveInventoryResponse> responseObserver) {
                try {
                        boolean reserved = inventoryAddApp.reserveInventory(
                                        request.getProductId(),
                                        request.getQuantity());

                        InventoryJpaEntity entity = inventorySelectApp.findAvailableInventory(request.getProductId(),
                                        request.getQuantity());

                        ReserveInventoryResponse response = ReserveInventoryResponse.newBuilder()
                                        .setSuccess(reserved)
                                        .setInventory(Inventory.newBuilder()
                                                        .setInventoryId(entity.getInventoryId())
                                                        .setProductId(entity.getProduct().getProductId())
                                                        .setWarehouseId(entity.getWareHouse().getWarehouseId())
                                                        .setTotalQuantity(entity.getTotalQuantity())
                                                        .setReservedQuantity(entity.getReservedQuantity())
                                                        .setAvailableQuantity(entity.getAvailableQuantity())
                                                        .build())
                                        .build();

                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                } catch (Exception e) {
                        responseObserver.onError(Status.INTERNAL.withDescription("Error reserving inventory")
                                        .asRuntimeException());
                }
        }

        @Override
        public void reserveInventories(
                        ReserveInventoriesRequest request,
                        StreamObserver<ReserveInventoriesResponse> responseObserver) {

                List<grpc.inventory.ReserveItem> items = request.getItemsList().stream()
                        .map(i -> grpc.inventory.ReserveItem.newBuilder()
                                .setProductId(i.getProductId())
                                .setWarehouseId(i.getWarehouseId())
                                .setQuantity(i.getQuantity())
                                .build())
                        .toList();

                Map<Long, Integer> productQuantities = items.stream()
                                .collect(Collectors.toMap(
                                                grpc.inventory.ReserveItem::getProductId,
                                                grpc.inventory.ReserveItem::getQuantity,
                                                Integer::sum // 중복 productId 있으면 수량 합산
                                ));
                ReserveResultDto result = inventoryAddApp.reserveInventories(productQuantities);

                ReserveInventoriesResponse.Builder builder = ReserveInventoriesResponse.newBuilder()
                                .setSuccess(result.success());

                result.failedItems().forEach(fi -> builder.addFailedItems(
                                grpc.inventory.FailedItem.newBuilder()
                                                .setProductId(fi.productId())
                                                .setWarehouseId(fi.warehouseId())
                                                .setRequested(fi.requested())
                                                .setAvailable(fi.available())
                                                .build()));

                result.successInventories().forEach(inv -> builder.addInventories(
                                grpc.inventory.Inventory.newBuilder()
                                                .setInventoryId(inv.inventoryId())
                                                .setProductId(inv.productId())
                                                .setWarehouseId(inv.warehouseId())
                                                .setTotalQuantity(inv.totalQuantity())
                                                .setReservedQuantity(inv.reservedQuantity())
                                                .setAvailableQuantity(inv.availableQuantity())
                                                .setReorderLevel(inv.reorderLevel())
                                                .setReorderQuantity(inv.reorderQuantity())
                                                .build()));

                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
        }

}
