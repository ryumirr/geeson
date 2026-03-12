package api.inventory.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SelectInventoryReq(
    @NotNull Long productId,
    @NotNull @Positive Integer quantity
) {
    // The application service will handle finding the entity
}