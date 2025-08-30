package app.inventory.app;

import org.springframework.stereotype.Service;

import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.repository.InventoryItemsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryItemsListApp {
    private final InventoryItemsRepository inventoryItemsRepository;

    /**
     * Find an inventory item by its ID.
     * 
     * @param id The ID of the inventory item.
     * @return The found inventory item entity, or null if not found.
     */
    public InventoryItemsJpaEntity findById(Long id) {
        return inventoryItemsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory Item not found: id=" + id));
    }

}
