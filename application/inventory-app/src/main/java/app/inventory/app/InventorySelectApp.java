package app.inventory.app;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class InventorySelectApp {
    private final InventoryRepository inventoryRepository;

    public InventorySelectApp(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<InventoryJpaEntity> findAvailableInventory(Long productId, int quantity) {
        List<InventoryJpaEntity> inventories = inventoryRepository.findByProductId(productId);

        // 예: 가장 재고가 양이 많은 warehouse
        return inventories.stream()
            .filter(inv -> inv.canReserve(quantity))
            .max(Comparator.comparing(inv -> inv.getWareHouse().getCapacity()));
    }


}
