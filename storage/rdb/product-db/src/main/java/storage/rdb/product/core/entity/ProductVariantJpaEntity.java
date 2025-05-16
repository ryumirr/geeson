package storage.rdb.product.core.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
public class ProductVariantJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;

    private Long productId;
    private String sku;
    private String optionCombination; // JSON 문자열로 저장
    private BigDecimal price;
    private Integer stock;
}
