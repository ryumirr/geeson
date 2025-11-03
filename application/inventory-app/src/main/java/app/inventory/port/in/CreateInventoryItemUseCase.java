package app.inventory.port.in;

public interface CreateInventoryItemUseCase {

    /**
     * 인벤토리 아이템 생성 명령
     */
    record CreateInventoryItemCommand(Long inventoryId,
                                      String serialNumber,
                                      String status) {}

    /**
     * 생성 결과 DTO
     */
    record CreateInventoryItemResult(Long inventoryItemId,
                                     Long inventoryId,
                                     Long batchLotId,
                                     String serialNumber,
                                     String status,
                                     String createdAt,
                                     String updatedAt) {}

    CreateInventoryItemResult handle(CreateInventoryItemCommand command);
}
