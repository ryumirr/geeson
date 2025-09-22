package app.inventory.mapper;

import app.inventory.dto.InventoryDto;
import domain.inventory.domain.entity.InventoryJpaEntity;

public class InventoryMapper {
    public static InventoryDto toDto(InventoryJpaEntity entity) {
        return new InventoryDto(
            entity.getInventoryId(),
            entity.getProduct().getProductId(),
            entity.getWareHouse().getWarehouseId(),
            entity.getTotalQuantity(),
            entity.getReservedQuantity(),
            entity.getAvailableQuantity(),
            entity.getReorderLevel(),
            entity.getReorderQuantity()
        );
    }
}
