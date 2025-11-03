package domain.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_prices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public ProductPriceJpaEntity(ProductJpaEntity product, BigDecimal price, BigDecimal discountPrice, String currency) {
        this.product = product;
        this.price = price;
        this.discountPrice = discountPrice;
        this.currency = currency;
        this.priceUpdatedAt = LocalDateTime.now();
    }
}
