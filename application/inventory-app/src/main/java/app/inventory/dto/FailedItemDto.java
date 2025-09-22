package app.inventory.dto;

public record FailedItemDto(
    Long productId,
    Long warehouseId,
    int requested,
    int available
) {}
