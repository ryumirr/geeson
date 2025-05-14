package rdb.storage.inventory.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class InventoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private Long productId; // 외부 상품 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WarehouseJpaEntity warehouse;

    private Integer totalQuantity;
    private Integer reservedQuantity;

    @Column(columnDefinition = "GENERATED ALWAYS AS (total_quantity - reserved_quantity) STORED")
    private Integer availableQuantity;

    private Integer reorderLevel;
    private Integer reorderQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
