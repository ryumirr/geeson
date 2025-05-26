package domain.product.domain.entity;

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