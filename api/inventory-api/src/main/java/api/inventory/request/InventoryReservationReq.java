package api.inventory.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryReservationReq(
    @NotNull @Positive Long inventoryId,
    @NotNull @Positive Long orderId,
    @NotNull @Positive Integer reservedQuantity,
    @Positive int ttlSeconds
) {}
