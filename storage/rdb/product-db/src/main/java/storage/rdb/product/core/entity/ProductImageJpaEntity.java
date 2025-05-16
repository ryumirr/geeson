package storage.rdb.product.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImageJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private Long productId;
    private String imageUrl;
    private Integer sortOrder;
    private Boolean isThumbnail;
}