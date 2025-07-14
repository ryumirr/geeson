package api.order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShipmentReq(
    @NotNull Long orderId,
    @NotBlank String carrier,
    @NotBlank String trackingNumber
) {}