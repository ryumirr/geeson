package api.inventory.response;

import domain.inventory.domain.entity.InventoryJpaEntity;

import java.time.LocalDateTime;

public record AddInventoryRes(
    Long inventoryId,
    Long productId,
    Long warehouseId,
    Long totalQuantity,
    Long reservedQuantity,
    Integer reorderLevel,
    Integer reorderQuantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static AddInventoryRes from(InventoryJpaEntity entity) {
        return new AddInventoryRes(
            entity.getInventoryId(),
            entity.getProduct().getProductId(),
            entity.getWareHouse().getWarehouseId(),
            entity.getTotalQuantity(),
            entity.getReservedQuantity(),
            entity.getReorderLevel(),
            entity.getReorderQuantity(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}