<<<<<<<< HEAD:storage/rdb/inventory-db/src/main/java/storage/rdb/inventory/core/entity/SupplierJpaEntity.java
package storage.rdb.inventory.core.entity;
========
package app.inventory.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/inventory-app/src/main/java/app/inventory/domain/entity/SupplierJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers")
public class SupplierJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String name;
    private String contactInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
