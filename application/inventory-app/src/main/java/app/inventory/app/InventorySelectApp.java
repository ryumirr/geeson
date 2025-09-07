package app.inventory.app;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class InventorySelectApp {
    private final InventoryRepository inventoryRepository;

    public InventorySelectApp(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Find available inventory for a product with sufficient quantity.
     * 
     * @param productId
     * @param quantity
     * @return InventoryJpaEntity
     * @throws IllegalArgumentException if no available inventory is found
     */
    public InventoryJpaEntity findAvailableInventory(Long productId, int quantity) {
        List<InventoryJpaEntity> inventories = inventoryRepository.findByProductId(productId);

        // 예: 가장 재고가 양이 많은 warehouse
        return inventories.stream()
            .filter(inv -> inv.canReserve(quantity))
            .max(Comparator.comparing(inv -> inv.getWareHouse().getCapacity()))
            .orElseThrow(() -> new IllegalArgumentException("Not enough inventory"));
    }
}
