package app.inventory.app;

import app.inventory.port.in.CreateWarehouseUseCase;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWarehouseApp implements CreateWarehouseUseCase {

    private final WarehouseRepository warehouseRepo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public CreateWarehouseResult handle(CreateWarehouseCommand command) {
        WarehouseJpaEntity entity = WarehouseJpaEntity.create(
                command.name(),
                command.location(),
                command.capacity()
        );

        WarehouseJpaEntity saved = warehouseRepo.save(entity);

        return new CreateWarehouseResult(
                saved.getWarehouseId(),
                saved.getName(),
                saved.getLocation(),
                saved.getCapacity(),
                saved.getCreatedAt().format(formatter),
                saved.getUpdatedAt().format(formatter)
        );
    }
}
