package api.inventory.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AddInventoryReq(
    @NotNull Long productId,
    @NotNull Long warehouseId,
    @NotNull @PositiveOrZero Long totalQuantity,
    @NotNull @PositiveOrZero Integer reorderLevel,
    @NotNull @Positive Integer reorderQuantity
) {
    // The application service will handle creating the entity
}