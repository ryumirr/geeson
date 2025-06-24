package api.inventory.response;

import java.time.LocalDateTime;
import domain.inventory.domain.entity.WarehouseJpaEntity;

public record RegisterWarehouseRes(
    Long warehouseId,
    String name,
    String location,
    Integer capacity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static RegisterWarehouseRes from(WarehouseJpaEntity entity) {
        return new RegisterWarehouseRes(
            entity.getWarehouseId(),
            entity.getName(),
            entity.getLocation(),
            entity.getCapacity(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
