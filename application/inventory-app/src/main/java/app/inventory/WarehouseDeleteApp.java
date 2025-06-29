package app.inventory.app;

import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseDeleteApp {
    private final WarehouseRepository warehouseRepo;

    public void deleteById(Long id) {
        warehouseRepo.deleteById(id);
    }
}