package app.product.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_tag_map")
public class ProductTagMapJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long tagId;
}