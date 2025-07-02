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
public class WarehouseListApp {
    private final WarehouseRepository warehouseRepo;

    public boolean existsById(Long id) {
        return warehouseRepo.existsById(id);
    }
    public List<WarehouseJpaEntity> findAll() {
        return warehouseRepo.findAll();
    }

}