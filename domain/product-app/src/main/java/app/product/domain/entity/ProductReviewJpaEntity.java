<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/ProductReviewJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/ProductReviewJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_reviews")
public class ProductReviewJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long productId;
    private Long customerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}