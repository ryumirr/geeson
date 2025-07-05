package api.inventory.response;

import domain.inventory.domain.entity.StockMovementJpaEntity;

public record StockMovementOutRes(
    Long movementId,
    String movementType,
    Integer quantity,
    String description
) {
    public static StockMovementOutRes from(StockMovementJpaEntity entity) {
        return new StockMovementOutRes(
            entity.getMovementId(),
            entity.getMovementType().name(),
            entity.getQuantity(),
            entity.getDescription()
        );
    }
}
