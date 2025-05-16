package storage.rdb.product.core.entity;

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