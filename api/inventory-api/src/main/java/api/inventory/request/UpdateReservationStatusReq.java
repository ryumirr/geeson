package api.inventory.request;

import jakarta.validation.constraints.NotNull;
import module.enums.ReservationStatus;

public record UpdateReservationStatusReq(
    @NotNull ReservationStatus status
) {
}