package rdb.storage.product.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category_map")
public class ProductCategoryMapJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long categoryId;
}