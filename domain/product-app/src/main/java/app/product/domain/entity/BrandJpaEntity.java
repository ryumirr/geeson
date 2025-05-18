<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/BrandJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/BrandJpaEntity.java

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class BrandJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String name;
    private String description;
}
