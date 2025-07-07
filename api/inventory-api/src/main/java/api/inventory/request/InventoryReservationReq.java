package api.inventory.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record InventoryReservationReq(
    @NotNull Long productId,
    @NotNull Long orderId,
    @Positive int reservedQuantity,
    LocalDateTime expiresAt
) {}