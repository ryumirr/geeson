package api.inventory.response;

import domain.inventory.domain.entity.InventoryItemsJpaEntity;

public record RegisterInventoryItemRes(
    Long inventoryItemId,
    Long inventoryId,
    Long batchLotId,
    String serialNumber,
    String status
) {
    public static RegisterInventoryItemRes from(InventoryItemsJpaEntity entity) {
        return new RegisterInventoryItemRes(
            entity.getInventoryItemId(),
            entity.getInventory().getInventoryId(),
            entity.getBatchLotId(),
            entity.getSerialNumber(),
            entity.getStatus()
        );
    }
}
