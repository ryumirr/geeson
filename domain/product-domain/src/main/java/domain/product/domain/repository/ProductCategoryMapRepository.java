package domain.product.domain.repository;

import domain.product.domain.entity.ProductCategoryMapJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryMapRepository  {
    ProductCategoryMapJpaEntity save(ProductCategoryMapJpaEntity productCategoryMap);
    
    /**
     * Find all category mappings for a specific product
     * @param productId the ID of the product
     * @return a list of product-category mappings
     */
    List<ProductCategoryMapJpaEntity> findByProductId(Long productId);
}