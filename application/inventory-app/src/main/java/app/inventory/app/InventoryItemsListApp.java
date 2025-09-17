package app.inventory.app;

import app.inventory.port.in.GetInventoryItemUseCase;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.repository.InventoryItemsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryItemsListApp implements GetInventoryItemUseCase {

    private final InventoryItemsRepository inventoryItemsRepository;

    @Override
    public InventoryItemResult getById(GetInventoryItemCommand command) {
        InventoryItemsJpaEntity entity = inventoryItemsRepository.findById(command.inventoryItemId())
                .orElseThrow(() -> new EntityNotFoundException("Inventory Item not found: id=" + command.inventoryItemId()));

        return new InventoryItemResult(
                entity.getInventoryItemId(),
                entity.getInventory().getInventoryId(),
                entity.getBatchLotId(),
                entity.getSerialNumber(),
                entity.getStatus(),
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt().toString()
        );
    }
}
