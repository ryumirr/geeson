package app.product.domain.entity;

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