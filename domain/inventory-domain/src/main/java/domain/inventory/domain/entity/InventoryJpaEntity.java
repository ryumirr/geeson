package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자
public class InventoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WarehouseJpaEntity warehouse;

    private Integer totalQuantity;
    private Integer reservedQuantity;

    private Integer reorderLevel;
    private Integer reorderQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드
    public static InventoryJpaEntity create(
            Long productId,
            WarehouseJpaEntity warehouse,
            int totalQuantity,
            int reservedQuantity,
            int reorderLevel,
            int reorderQuantity
    ) {
        LocalDateTime now = LocalDateTime.now();
        InventoryJpaEntity entity = new InventoryJpaEntity();
        entity.productId = productId;
        entity.warehouse = warehouse;
        entity.totalQuantity = totalQuantity;
        entity.reservedQuantity = reservedQuantity;
        entity.reorderLevel = reorderLevel;
        entity.reorderQuantity = reorderQuantity;
        entity.createdAt = now;
        entity.updatedAt = now;
        return entity;
    }

    @Transient
    public int getAvailableQuantity() {
        return totalQuantity - reservedQuantity;
    }

    /**
     * 예약했던 수량을 취소하거나 해제함
     * @param quantity
     */
    public void release(int quantity) {
        if (reservedQuantity < quantity) {
            throw new IllegalArgumentException("Trying to release more than reserved.");
        }
        this.reservedQuantity -= quantity;
        touch();
    }

    /**
     * 새로운 물량 입고
     * @param quantity
     */
    public void restock(int quantity) {
        this.totalQuantity += quantity;
        touch();
    }

    /**
     * 엔티티의 변경을 추적하기 위해 updatedAt을 현재 시간으로 갱신
     */
    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}