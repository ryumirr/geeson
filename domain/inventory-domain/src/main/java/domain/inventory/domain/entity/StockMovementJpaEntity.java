package domain.inventory.domain.entity;

import module.enums.MovementType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StockMovementJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long movementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryJpaEntity inventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "movement_date", nullable = true,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime movementDate;

    @Column(name = "reference_id", nullable = false, unique = true, length = 128)
    private String referenceId;

    @PrePersist
    protected void onCreate() {
        if (this.movementDate == null) {
            this.movementDate = LocalDateTime.now();
        }
    }

    // 팩토리 메서드
    public static StockMovementJpaEntity create(Long inventoryId,
                                                MovementType movementType,
                                                Integer quantity,
                                                String description,
                                                String referenceId) {
        StockMovementJpaEntity entity = new StockMovementJpaEntity();
        entity.inventory = InventoryJpaEntity.withId(inventoryId);
        entity.movementType = movementType;
        entity.quantity = quantity;
        entity.description = description;
        entity.referenceId = referenceId;
        return entity;
    }

    public void markAsStockIn() {
        this.movementType = MovementType.IN;
    }

    public void markAsStockOut() {
        this.movementType = MovementType.OUT;
    }
}
