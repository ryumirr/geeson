package domain.product.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_categories")
public class ProductCategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private Long parentId;
    private Integer depth;
}