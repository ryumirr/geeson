package app.inventory.dto;

import lombok.Value;
import module.enums.MovementType;
import java.time.LocalDateTime;

import domain.inventory.domain.entity.StockMovementJpaEntity;

@Value
public class StockMovementResult {
    Long movementId;
    Long inventoryId;        // 엔티티에서 inventory.getInventoryId()로 매핑
    MovementType movementType;
    int quantity;
    LocalDateTime movementDate;  // 엔티티와 동일하게 LocalDateTime으로
    String description;
    String referenceId;

    public static StockMovementResult fromEntity(StockMovementJpaEntity entity) {
        return new StockMovementResult(
                entity.getMovementId(),
                entity.getInventory() != null ? entity.getInventory().getInventoryId() : null,
                entity.getMovementType(),
                entity.getQuantity(),
                entity.getMovementDate(),
                entity.getDescription(),
                entity.getReferenceId()
        );
    }
}
