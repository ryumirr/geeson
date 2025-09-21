package app.inventory.app;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.entity.ProductJpaEntity;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryAddApp {

    private final InventoryRepository inventoryRepository;

    /**
     * Add new inventory for a product in a warehouse
     *
     * @param productId       The product ID
     * @param warehouseId     The warehouse ID
     * @param totalQuantity   The total quantity
     * @param reorderLevel    The reorder level
     * @param reorderQuantity The reorder quantity
     * @return The created inventory entity
     */
    public InventoryJpaEntity addInventory(
            Long productId,
            Long warehouseId,
            Integer totalQuantity,
            Integer reorderLevel,
            Integer reorderQuantity) {
        // Create product reference
        ProductJpaEntity product = new ProductJpaEntity();
        try {
            java.lang.reflect.Field field = ProductJpaEntity.class.getDeclaredField("productId");
            field.setAccessible(true);
            field.set(product, productId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product reference", e);
        }

        // Create warehouse reference
        WarehouseJpaEntity warehouse = null;
        try {
            // Use reflection to create a new instance with the constructor
            java.lang.reflect.Constructor<WarehouseJpaEntity> constructor = WarehouseJpaEntity.class
                    .getDeclaredConstructor();
            constructor.setAccessible(true);
            warehouse = constructor.newInstance();

            // Set the ID field
            java.lang.reflect.Field field = WarehouseJpaEntity.class.getDeclaredField("warehouseId");
            field.setAccessible(true);
            field.set(warehouse, warehouseId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create warehouse reference", e);
        }

        // Create a new inventory entity
        InventoryJpaEntity inventory = InventoryJpaEntity.builder()
                .product(product)
                .wareHouse(warehouse)
                .totalQuantity(totalQuantity)
                .reservedQuantity(0)
                .reorderLevel(reorderLevel)
                .reorderQuantity(reorderQuantity)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Save and return the entity
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public boolean reserveInventory(Long productId, int quantity) {
        InventoryJpaEntity inventory = inventoryRepository.findByProductId(productId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Inventory not found for productId=" + productId));

        // 재고 부족
        if (inventory.canReserve(quantity) == false) {
            return false;
        }

        // availableQuantity는 Generated Column이라 따로 set 안 함
        inventory.reserve(inventory.getReservedQuantity() + quantity);

        inventoryRepository.save(inventory);
        return true;
    }

}