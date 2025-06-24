package domain.inventory.service;

import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepo;

    public WarehouseJpaEntity register(WarehouseJpaEntity entity) {
        return warehouseRepo.save(entity);
    }

    public List<WarehouseJpaEntity> registerAll(List<WarehouseJpaEntity> entities) {
        return warehouseRepo.saveAll(entities);
    }

    public List<WarehouseJpaEntity> findAll() {
        return warehouseRepo.findAll();
    }

    public void deleteById(Long id) {
        warehouseRepo.deleteById(id);
    }

    public boolean existsById(Long id) {
        return warehouseRepo.existsById(id);
    }
}