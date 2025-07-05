package api.inventory.response;

import java.time.LocalDateTime;

public record InventoryReservationRes(
    Long reservationId,
    Long inventoryId,
    Long orderId,
    int reservedQuantity,
    LocalDateTime reservedAt,
    LocalDateTime expiresAt,
    String status
) {}