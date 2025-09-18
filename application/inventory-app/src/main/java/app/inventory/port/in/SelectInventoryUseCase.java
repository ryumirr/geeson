package app.inventory.port.in;

public interface SelectInventoryUseCase {

    /**
     * 재고 조회 명령
     */
    record SelectInventoryCommand(Long productId, Integer quantity) {}

    /**
     * 조회 결과 DTO
     */
    record SelectInventoryResult(Long inventoryId,
                                 Long productId,
                                 Long warehouseId,
                                 Integer totalQuantity,
                                 Integer reservedQuantity,
                                 Integer availableQuantity,
                                 Integer reorderLevel,
                                 Integer reorderQuantity,
                                 String createdAt,
                                 String updatedAt) {}

    SelectInventoryResult handle(SelectInventoryCommand command);
}
