<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/StockMovementJpaEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/StockMovementJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovementJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    private String movementType; // IN, OUT, TRANSFER
    private Integer quantity;
    private String description;
    private LocalDateTime movementDate;
}
