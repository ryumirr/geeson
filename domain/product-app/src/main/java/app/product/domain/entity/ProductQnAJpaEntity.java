<<<<<<<< HEAD:storage/rdb/product-db/src/main/java/storage/rdb/product/core/entity/ProductQnAJpaEntity.java
package storage.rdb.product.core.entity;
========
package app.product.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/product-app/src/main/java/app/product/domain/entity/ProductQnAJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_qna")
public class ProductQnAJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaId;

    private Long productId;
    private Long customerId;
    private String question;
    private String answer;
    private String status;
    private LocalDateTime createdAt;
}
