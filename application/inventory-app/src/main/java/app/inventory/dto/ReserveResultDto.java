package app.inventory.dto;

import java.util.List;

import app.inventory.dto.FailedItemDto;

public record ReserveResultDto(
    boolean success,
    List<FailedItemDto> failedItems,
    List<InventoryDto> successInventories
) {}
