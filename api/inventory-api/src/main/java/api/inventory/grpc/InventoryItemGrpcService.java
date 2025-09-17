package api.inventory.grpc;

import app.inventory.port.in.CreateInventoryItemUseCase;
import app.inventory.port.in.GetInventoryItemUseCase;
import grpc.inventory.CreateInventoryItemRequest;
import grpc.inventory.GetInventoryItemRequest;
import grpc.inventory.InventoryItem;
import grpc.inventory.InventoryItemResponse;
import grpc.inventory.InventoryItemServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class InventoryItemGrpcService extends InventoryItemServiceGrpc.InventoryItemServiceImplBase {

    private final CreateInventoryItemUseCase createInventoryItemUseCase;
    private final GetInventoryItemUseCase getInventoryItemUseCase;

    @Override
    public void createInventoryItem(CreateInventoryItemRequest request,
                                    StreamObserver<InventoryItemResponse> responseObserver) {
        try {
            var result = createInventoryItemUseCase.handle(
                new CreateInventoryItemUseCase.CreateInventoryItemCommand(
                    request.getInventoryId(),
                    request.getSerialNumber(),
                    request.getStatus()
                )
            );

            InventoryItem item = InventoryItem.newBuilder()
                    .setInventoryItemId(result.inventoryItemId())
                    .setInventoryId(result.inventoryId())
                    .setBatchLotId(result.batchLotId())
                    .setSerialNumber(result.serialNumber())
                    .setStatus(result.status())
                    .setCreatedAt(result.createdAt())
                    .setUpdatedAt(result.updatedAt())
                    .build();

            InventoryItemResponse response = InventoryItemResponse.newBuilder()
                    .setItem(item)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                Status.INTERNAL.withDescription("Unexpected server error").asRuntimeException()
            );
        }
    }

    @Override
    public void getInventoryItem(GetInventoryItemRequest request,
                                 StreamObserver<InventoryItemResponse> responseObserver) {
        try {
            var result = getInventoryItemUseCase.getById(
                new GetInventoryItemUseCase.GetInventoryItemCommand(request.getInventoryItemId())
            );

            InventoryItem item = InventoryItem.newBuilder()
                    .setInventoryItemId(result.inventoryItemId())
                    .setInventoryId(result.inventoryId())
                    .setBatchLotId(result.batchLotId())
                    .setSerialNumber(result.serialNumber())
                    .setStatus(result.status())
                    .setCreatedAt(result.createdAt())
                    .setUpdatedAt(result.updatedAt())
                    .build();

            InventoryItemResponse response = InventoryItemResponse.newBuilder()
                    .setItem(item)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                Status.INTERNAL.withDescription("Unexpected server error").asRuntimeException()
            );
        }
    }
}
