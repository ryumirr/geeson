package api.inventory.grpc;

import grpc.warehouse.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@GrpcService
@RequiredArgsConstructor
public class WareHouseGrpcService extends WarehouseServiceGrpc.WarehouseServiceImplBase {

    private final WarehouseRepository warehouseRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * 창고 생성
     */
    @Override
    public void createWarehouse(CreateWarehouseRequest request, StreamObserver<CreateWarehouseResponse> responseObserver) {
        try {
            WarehouseJpaEntity entity = WarehouseJpaEntity.create(
                    request.getName(),
                    request.getLocation(),
                    request.getCapacity()
            );

            WarehouseJpaEntity saved = warehouseRepository.save(entity);

            CreateWarehouseResponse response = CreateWarehouseResponse.newBuilder()
                    .setWarehouseId(saved.getWarehouseId())
                    .setName(saved.getName())
                    .setLocation(saved.getLocation())
                    .setCapacity(saved.getCapacity())
                    .setCreatedAt(saved.getCreatedAt().format(formatter))
                    .setUpdatedAt(saved.getUpdatedAt().format(formatter))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * 창고 단건 조회
     */
    @Override
    public void getWarehouse(GetWarehouseRequest request, StreamObserver<GetWarehouseResponse> responseObserver) {
        try {
            WarehouseJpaEntity entity = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found: " + request.getWarehouseId()));

            GetWarehouseResponse response = GetWarehouseResponse.newBuilder()
                    .setWarehouseId(entity.getWarehouseId())
                    .setName(entity.getName())
                    .setLocation(entity.getLocation())
                    .setCapacity(entity.getCapacity())
                    .setCreatedAt(entity.getCreatedAt().format(formatter))
                    .setUpdatedAt(entity.getUpdatedAt().format(formatter))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /**
     * 창고 정보 수정
     */
    @Override
    public void updateWarehouse(UpdateWarehouseRequest request, StreamObserver<UpdateWarehouseResponse> responseObserver) {
        try {
            WarehouseJpaEntity entity = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found: " + request.getWarehouseId()));

            entity.updateInfo(request.getName(), request.getLocation(), request.getCapacity());
            WarehouseJpaEntity updated = warehouseRepository.save(entity);

            UpdateWarehouseResponse response = UpdateWarehouseResponse.newBuilder()
                    .setWarehouseId(updated.getWarehouseId())
                    .setName(updated.getName())
                    .setLocation(updated.getLocation())
                    .setCapacity(updated.getCapacity())
                    .setCreatedAt(updated.getCreatedAt().format(formatter))
                    .setUpdatedAt(updated.getUpdatedAt().format(formatter))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
