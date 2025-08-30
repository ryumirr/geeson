package app.inventory.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.inventory.command.RegisterInventoryItemCommand;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.repository.InventoryItemsRepository;
import domain.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryItemsRegisterApp {

    private final InventoryItemsRepository inventoryItemsRepository;
    private final InventoryRepository inventoryRepository;

    public InventoryItemsJpaEntity registerInventoryItem(RegisterInventoryItemCommand command) {
        InventoryJpaEntity inventory = inventoryRepository.findById(command.inventoryId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found: id=" + command.inventoryId()));

        InventoryItemsJpaEntity entity = InventoryItemsJpaEntity.from(
                inventory,
                this.createBatchLotId(command.inventoryId()),
                command.serialNumber(),
                command.status());

        return inventoryItemsRepository.save(entity);
    }

    private Long createBatchLotId(Long orderId) {
        // Implement logic to create a unique batch lot ID based on the order ID, carrier, and tracking number
        return orderId * 1000 + System.currentTimeMillis() % 1000;

    }
}