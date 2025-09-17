package app.inventory.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.inventory.port.in.CreateInventoryItemUseCase;
import app.inventory.port.in.CreateInventoryItemUseCase.CreateInventoryItemCommand;
import app.inventory.port.in.CreateInventoryItemUseCase.CreateInventoryItemResult;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.repository.InventoryItemsRepository;
import domain.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryItemsRegisterApp implements CreateInventoryItemUseCase {

    private final InventoryItemsRepository inventoryItemsRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public CreateInventoryItemResult handle(CreateInventoryItemCommand command) {
        InventoryJpaEntity inventory = inventoryRepository.findById(command.inventoryId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found: id=" + command.inventoryId()));

        InventoryItemsJpaEntity entity = InventoryItemsJpaEntity.from(
                inventory,
                this.createBatchLotIdByOrderId(command.inventoryId()),
                command.serialNumber(),
                command.status());

        InventoryItemsJpaEntity saved = inventoryItemsRepository.save(entity);

        return new CreateInventoryItemResult(
                saved.getInventoryItemId(),
                saved.getInventory().getInventoryId(),
                saved.getBatchLotId(),
                saved.getSerialNumber(),
                saved.getStatus(),
                saved.getCreatedAt().toString(),
                saved.getUpdatedAt().toString()
        );
    }

    private Long createBatchLotIdByOrderId(Long orderId) {
        return orderId * 1000 + System.currentTimeMillis() % 1000;
    }
}
