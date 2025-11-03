package api.inventory.request;

import jakarta.validation.constraints.NotNull;

public record RegisterInventoryItemReq(
    @NotNull Long inventoryId,
    Long batchLotId,
    String serialNumber,
    String status
) {}
