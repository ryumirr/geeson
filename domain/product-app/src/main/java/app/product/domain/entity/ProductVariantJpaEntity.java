<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/ProductVariantJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/ProductVariantJpaEntity.java

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
public class ProductVariantJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;

    private Long productId;
    private String sku;
    private String optionCombination; // JSON 문자열로 저장
    private BigDecimal price;
    private Integer stock;
}
