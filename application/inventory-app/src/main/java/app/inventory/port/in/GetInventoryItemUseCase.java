package app.inventory.port.in;

public interface GetInventoryItemUseCase {
    record GetInventoryItemCommand(Long inventoryItemId) {}
    record GetInventoryItemBySerialCommand(String serialNumber) {}

    record InventoryItemResult(Long inventoryItemId,
                               Long inventoryId,
                               Long batchLotId,
                               String serialNumber,
                               String status,
                               String createdAt,
                               String updatedAt) {}

    InventoryItemResult getById(GetInventoryItemCommand command);
    InventoryItemResult getBySerialNumber(GetInventoryItemBySerialCommand command);
}
