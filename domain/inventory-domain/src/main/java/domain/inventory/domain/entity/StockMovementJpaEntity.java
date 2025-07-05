package domain.inventory.domain.entity;

import module.enums.MovementType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "stock_movements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private String description;

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;

    @PrePersist
    protected void onCreate() {
        this.movementDate = LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static StockMovementJpaEntity create(
            Long inventoryId,
            MovementType movementType,
            Integer quantity,
            String description) {
        StockMovementJpaEntity entity = new StockMovementJpaEntity();
        entity.inventory = InventoryJpaEntity.withId(inventoryId); // FK 연관 객체 세팅
        entity.movementType = movementType;
        entity.quantity = quantity;
        entity.description = description;
        return entity;
    }

    public void markAsStockIn() {
        this.movementType = MovementType.IN;
    }

    public void markAsStockOut() {
        this.movementType = MovementType.OUT;
    }
}
