package storage.rdb.product.core.entity;

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
