<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/InventoryReservationJapEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/InventoryReservationJapEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_reservations")
public class InventoryReservationJapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    private Long orderId;
    private Integer reservedQuantity;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private String status;
    private Long customerId;
    private Long productId;
    private Long warehouseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
