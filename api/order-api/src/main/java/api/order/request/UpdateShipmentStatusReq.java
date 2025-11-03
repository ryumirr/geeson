package api.order.request;

import jakarta.validation.constraints.NotNull;
import module.enums.ShipmentStatus;

public record UpdateShipmentStatusReq(
    @NotNull ShipmentStatus status
) {}