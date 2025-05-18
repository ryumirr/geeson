<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/ProductDetailJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/ProductDetailJpaEntity.java

import jakarta.persistence.*;

@Entity
@Table(name = "product_details")
public class ProductDetailJpaEntity {
    @Id
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    private String description;
    private String spec;
    private String warranty;
}
