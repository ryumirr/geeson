package app.inventory.command;

public record RegisterInventoryItemCommand(
    Long inventoryId,
    String serialNumber,
    String status
) {}
