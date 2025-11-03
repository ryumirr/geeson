package domain.product.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_category_map", schema = "product_db")
@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryMapJpaEntity {

    @EmbeddedId
    private ProductCategoryMapId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private ProductCategoryJpaEntity category;

    public ProductCategoryMapJpaEntity(ProductJpaEntity product, ProductCategoryJpaEntity category) {
        this.product = product;
        this.category = category;
        this.id = new ProductCategoryMapId(product.getProductId(), category.getCategoryId());
    }


    @Embeddable
    public static class ProductCategoryMapId implements Serializable {

        @Column(name = "product_id")
        private Long productId;

        @Column(name = "category_id")
        private Long categoryId;

        // Constructors, getters, setters, equals, and hashCode

        public ProductCategoryMapId() {}

        public ProductCategoryMapId(Long productId, Long categoryId) {
            this.productId = productId;
            this.categoryId = categoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductCategoryMapId that = (ProductCategoryMapId) o;
            return Objects.equals(productId, that.productId) &&
                Objects.equals(categoryId, that.categoryId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, categoryId);
        }
    }

}