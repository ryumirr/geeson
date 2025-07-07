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
    private Long totalQuantity;

    @Column(name = "reserved_quantity")
    private Long reservedQuantity;

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

    public boolean canReserve(Integer quantity) {
        return this.totalQuantity >= quantity;
    }

    public void reserve(Integer quantity) {
        this.reservedQuantity += quantity;
    }

    public void release(Integer quantity) {
        this.reservedQuantity -= quantity;
    }
}