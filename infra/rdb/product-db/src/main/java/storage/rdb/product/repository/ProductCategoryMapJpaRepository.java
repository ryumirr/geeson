package storage.rdb.product.repository;

import domain.product.domain.entity.ProductCategoryMapJpaEntity;
import domain.product.domain.repository.ProductCategoryMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductCategoryMapJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryMapJpaRepository implements ProductCategoryMapRepository {
    private final SpringDataProductCategoryMapJpaRepository repository;
    
    @Override
    public ProductCategoryMapJpaEntity save(ProductCategoryMapJpaEntity productCategoryMap) {
        return repository.save(productCategoryMap);
    }
    
    @Override
    public List<ProductCategoryMapJpaEntity> findByProductId(Long productId) {
        return repository.findByProductId(productId);
    }
}