package domain.product.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class BrandJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    private String name;
    private String description;
}
