<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/InventoryJpaEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/InventoryJpaEntity.java

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
