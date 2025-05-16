package storage.rdb.product.core.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_prices")
public class ProductPriceJpaEntity {
    @Id
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    private BigDecimal price;
    private BigDecimal discountPrice;
    private String currency;
    private LocalDateTime priceUpdatedAt;
}
