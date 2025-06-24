package api.inventory.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import domain.inventory.domain.entity.WarehouseJpaEntity;

public record RegisterWarehousesReq(
    @NotBlank String name,
    @NotBlank String location,
    @Positive Integer capacity
) {
    public WarehouseJpaEntity toOneEntity() {
        return WarehouseJpaEntity.create(name, location, capacity);
    }
}