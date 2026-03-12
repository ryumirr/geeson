package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WarehouseJpaEntity wareHouse;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "reorder_quantity")
    private Integer reorderQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static InventoryJpaEntity withId(Long inventoryId) {
        InventoryJpaEntity entity = new InventoryJpaEntity();
        entity.inventoryId = inventoryId;
        return entity;
    }

    /** 예약 가능 여부 확인 */
    public boolean canReserve(Integer quantity) {
        return getAvailableQuantity() >= quantity;
    }

    /** 예약 처리 */
    public void reserve(Integer quantity) {
        this.reservedQuantity = (this.reservedQuantity != null ? this.reservedQuantity : 0) + quantity;
    }

    /** 예약 해제 */
    public void release(Integer quantity) {
        this.reservedQuantity = (this.reservedQuantity != null ? this.reservedQuantity : 0) - quantity;
    }

    /** 가용 재고 계산 */
    public int getAvailableQuantity() {
        return (this.totalQuantity != null ? this.totalQuantity.intValue() : 0)
             - (this.reservedQuantity != null ? this.reservedQuantity.intValue() : 0);
    }
}
