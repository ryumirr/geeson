package api.inventory.grpc;

import grpc.inventory.AddStockMovementRequest;
import grpc.inventory.AddStockMovementResponse;
import grpc.inventory.GetStockMovementsByInventoryRequest;
import grpc.inventory.GetStockMovementsByInventoryResponse;
import grpc.inventory.GetStockMovementByReferenceRequest;
import grpc.inventory.GetStockMovementByReferenceResponse;
import grpc.inventory.StockMovementServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import module.enums.MovementType;

import java.util.List;

import app.inventory.dto.AddStockMovementCommand;
import app.inventory.dto.StockMovementResult;
import app.inventory.port.in.AddStockMovementUseCase;
import app.inventory.port.in.GetStockMovementsByInventoryUseCase;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import app.inventory.port.in.GetStockMovementByReferenceUseCase;

@Slf4j
@RequiredArgsConstructor
@GrpcService
public class StockMovementGrpcService extends StockMovementServiceGrpc.StockMovementServiceImplBase {

    private final AddStockMovementUseCase addStockMovementUseCase;
    private final GetStockMovementsByInventoryUseCase getStockMovementsByInventoryUseCase;
    private final GetStockMovementByReferenceUseCase getStockMovementByReferenceUseCase;

    @Override
    public void addStockMovement(AddStockMovementRequest request,
            StreamObserver<AddStockMovementResponse> responseObserver) {
        try {
            var command = new AddStockMovementCommand(
                    request.getInventoryId(),
                    MovementType.valueOf(request.getMovementType().name()),
                    request.getQuantity(),
                    request.getDescription(),
                    request.getReferenceId());

            StockMovementResult result = addStockMovementUseCase.add(command);

            AddStockMovementResponse response = AddStockMovementResponse.newBuilder()
                    .setStockMovement(toGrpcStockMovement(result))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error while adding stock movement", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Failed to add stock movement").withCause(e).asRuntimeException());
        }
    }

    @Override
    public void getStockMovementsByInventory(GetStockMovementsByInventoryRequest request,
            StreamObserver<GetStockMovementsByInventoryResponse> responseObserver) {
        try {
            var entities = getStockMovementsByInventoryUseCase
                    .findByInventory_InventoryId(request.getInventoryId()); // List<StockMovementJpaEntity>

            var response = GetStockMovementsByInventoryResponse.newBuilder()
                    .addAllStockMovements(entities.stream().map(this::toEntityStockMovement).toList())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error while fetching stock movements by inventory", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Failed to fetch stock movements").withCause(e)
                            .asRuntimeException());
        }
    }

    @Override
    public void getStockMovementByReference(GetStockMovementByReferenceRequest request,
            StreamObserver<GetStockMovementByReferenceResponse> responseObserver) {
        try {
            StockMovementJpaEntity result = getStockMovementByReferenceUseCase.findByReferenceId(request.getReferenceId());

            GetStockMovementByReferenceResponse response = GetStockMovementByReferenceResponse.newBuilder()
                    .setStockMovement(toEntityStockMovement(result))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error while fetching stock movement by referenceId", e);
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription("Stock movement not found").withCause(e).asRuntimeException());
        }
    }

    private grpc.inventory.StockMovement toGrpcStockMovement(StockMovementResult result) {
        return grpc.inventory.StockMovement.newBuilder()
                .setMovementId(result.getMovementId())
                .setInventoryId(result.getInventoryId())
                .setMovementType(grpc.inventory.MovementType.valueOf(result.getMovementType().name()))
                .setQuantity(result.getQuantity())
                .setMovementDate(result.getMovementDate().toString()) // LocalDateTime → ISO-8601 string
                .setDescription(result.getDescription() != null ? result.getDescription() : "")
                .setReferenceId(result.getReferenceId())
                .build();
    }

    // Entity → proto
    private grpc.inventory.StockMovement toEntityStockMovement(
            domain.inventory.domain.entity.StockMovementJpaEntity e) {
        return grpc.inventory.StockMovement.newBuilder()
                .setMovementId(e.getMovementId())
                .setInventoryId(e.getInventory() != null ? e.getInventory().getInventoryId() : 0L)
                .setMovementType(grpc.inventory.MovementType.valueOf(e.getMovementType().name()))
                .setQuantity(e.getQuantity())
                .setMovementDate(e.getMovementDate().toString())
                .setDescription(e.getDescription() != null ? e.getDescription() : "")
                .setReferenceId(e.getReferenceId())
                .build();
    }
}
