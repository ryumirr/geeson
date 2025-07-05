package api.inventory.request;

public record StockMovementOutReq(
    Long inventoryId,
    Integer quantity,
    String description
) {}