package api.inventory.response;

import app.inventory.port.in.CreateInventoryItemUseCase.CreateInventoryItemResult;
import app.inventory.port.in.GetInventoryItemUseCase.InventoryItemResult;

public record RegisterInventoryItemRes(
        Long inventoryItemId,
        Long inventoryId,
        Long batchLotId,
        String serialNumber,
        String status,
        String createdAt,
        String updatedAt
) {
    public static RegisterInventoryItemRes from(CreateInventoryItemResult result) {
        return new RegisterInventoryItemRes(
                result.inventoryItemId(),
                result.inventoryId(),
                result.batchLotId(),
                result.serialNumber(),
                result.status(),
                result.createdAt(),
                result.updatedAt()
        );
    }

    public static RegisterInventoryItemRes from(InventoryItemResult result) {
        return new RegisterInventoryItemRes(
                result.inventoryItemId(),
                result.inventoryId(),
                result.batchLotId(),
                result.serialNumber(),
                result.status(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
