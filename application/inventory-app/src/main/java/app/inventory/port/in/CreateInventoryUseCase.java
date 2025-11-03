package app.inventory.port.in;

public interface CreateInventoryUseCase {

    /**
     * 인벤토리 생성 명령
     */
    record CreateInventoryCommand(Long productId,
                                  Long warehouseId,
                                  Integer totalQuantity,
                                  Integer reorderLevel,
                                  Integer reorderQuantity) {}

    /**
     * 생성 결과 DTO
     */
    record CreateInventoryResult(Long inventoryId,
                                 Long productId,
                                 Long warehouseId,
                                 Integer totalQuantity,
                                 Integer reservedQuantity,
                                 Integer availableQuantity,
                                 Integer reorderLevel,
                                 Integer reorderQuantity,
                                 String createdAt,
                                 String updatedAt) {}

    CreateInventoryResult handle(CreateInventoryCommand command);
}
