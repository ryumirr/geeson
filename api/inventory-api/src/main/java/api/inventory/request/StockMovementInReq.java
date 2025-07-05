package api.inventory.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import module.enums.MovementType;

public record StockMovementInReq(

    @NotNull(message = "Inventory ID must not be null")
    Long inventoryId,

    @NotNull(message = "Movement type must be provided")
    MovementType movementType,

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be a positive number")
    Integer quantity,

    String description,

    @NotBlank(message = "Idempotency key must not be blank")
    String idempotencyKey

) {
    public StockMovementJpaEntity toEntity() {
        return StockMovementJpaEntity.create(
            inventoryId,
            movementType,
            quantity,
            description
        );
    }
}
