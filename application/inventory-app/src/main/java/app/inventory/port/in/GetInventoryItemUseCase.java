package app.inventory.port.in;

public interface GetInventoryItemUseCase {

    record GetInventoryItemCommand(Long inventoryItemId) {}

    record InventoryItemResult(Long inventoryItemId,
                               Long inventoryId,
                               Long batchLotId,
                               String serialNumber,
                               String status,
                               String createdAt,
                               String updatedAt) {}

    InventoryItemResult getById(GetInventoryItemCommand command);
}
