<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/InventoryAdjustmentJpaEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/InventoryAdjustmentJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_adjustments")
public class InventoryAdjustmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adjustmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    private Integer adjustmentQuantity;
    private String reason;
    private LocalDateTime adjustedAt;
}
