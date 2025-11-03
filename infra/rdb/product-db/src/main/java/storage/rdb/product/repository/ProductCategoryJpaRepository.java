package storage.rdb.product.repository;

import domain.product.domain.entity.ProductCategoryJpaEntity;
import domain.product.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductCategoryJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryJpaRepository implements ProductCategoryRepository {
    private final SpringDataProductCategoryJpaRepository repository;
    
    @Override
    public ProductCategoryJpaEntity save(ProductCategoryJpaEntity category) {
        return repository.save(category);
    }
    
    @Override
    public Optional<ProductCategoryJpaEntity> findById(Long categoryId) {
        return repository.findById(categoryId);
    }
    
    @Override
    public List<ProductCategoryJpaEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategoryJpaEntity> findAllByIds(List<Long> categoryId) {
        return repository.findAllById(categoryId);
    }

    @Override
    public Optional<ProductCategoryJpaEntity> findByName(String name) {
        return repository.findByName(name);
    }
}