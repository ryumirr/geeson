<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/ProductOptionJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/ProductOptionJpaEntity.java

import jakarta.persistence.*;

@Entity
@Table(name = "product_options")
public class ProductOptionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    private Long productId;
    private String name;
}