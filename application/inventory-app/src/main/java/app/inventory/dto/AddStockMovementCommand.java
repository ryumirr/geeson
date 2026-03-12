package app.inventory.dto;

import lombok.Value;
import module.enums.MovementType;

@Value
public class AddStockMovementCommand {
    Long inventoryId;
    MovementType movementType;
    int quantity;
    String description;
    String referenceId;
}
