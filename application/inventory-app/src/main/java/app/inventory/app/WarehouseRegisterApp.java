package app.inventory.app;

import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseRegisterApp {
    private final WarehouseRepository warehouseRepo;

    public WarehouseJpaEntity register(WarehouseJpaEntity entity) {
        return warehouseRepo.save(entity);
    }

    public List<WarehouseJpaEntity> registerAll(List<WarehouseJpaEntity> entities) {
        return warehouseRepo.saveAll(entities);
    }
}