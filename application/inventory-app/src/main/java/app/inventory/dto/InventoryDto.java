package app.inventory.dto;

public record InventoryDto(
    Long inventoryId,
    Long productId,
    Long warehouseId,
    int totalQuantity,
    int reservedQuantity,
    int availableQuantity,
    int reorderLevel,
    int reorderQuantity
) {}
