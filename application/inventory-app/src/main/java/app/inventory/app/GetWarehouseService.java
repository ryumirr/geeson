package app.inventory.app;

import app.inventory.port.in.GetWarehouseUseCase;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWarehouseService implements GetWarehouseUseCase {

    private final WarehouseRepository warehouseRepo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public GetWarehouseResult handle(GetWarehouseQuery query) {
        WarehouseJpaEntity entity = warehouseRepo.findById(query.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + query.warehouseId()));

        return new GetWarehouseResult(
                entity.getWarehouseId(),
                entity.getName(),
                entity.getLocation(),
                entity.getCapacity(),
                entity.getCreatedAt().format(formatter),
                entity.getUpdatedAt().format(formatter)
        );
    }
}
