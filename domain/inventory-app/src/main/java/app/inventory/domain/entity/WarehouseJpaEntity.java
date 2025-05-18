<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/WarehouseJpaEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/WarehouseJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
public class WarehouseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;
    private String name;
    private String location;
    private Integer capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
