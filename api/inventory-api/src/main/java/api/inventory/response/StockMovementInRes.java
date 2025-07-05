package api.inventory.response;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import module.enums.MovementType;

import java.time.LocalDateTime;

public record StockMovementInRes(
    Long movementId,
    Long inventoryId,
    MovementType movementType,
    Integer quantity,
    String description,
    LocalDateTime movementDate
) {
    public static StockMovementInRes from(StockMovementJpaEntity entity) {
        return new StockMovementInRes(
            entity.getMovementId(),
            entity.getInventory().getInventoryId(),
            entity.getMovementType(),
            entity.getQuantity(),
            entity.getDescription(),
            entity.getMovementDate()
        );
    }
}