package domain.product.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_option_values")
public class ProductOptionValueJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    private Long optionId;
    private String value;
}