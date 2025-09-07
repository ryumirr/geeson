package domain.product.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductCategoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private String description;
    private Long parentId;
    private Integer depth;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ProductCategoryJpaEntity(Long categoryId, String name, String description, Long parentId, Integer depth,
                                    Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
        this.depth = depth;
        this.isActive = isActive;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }
}
