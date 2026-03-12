package domain.product.domain.repository;

import domain.product.domain.entity.ProductCategoryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository {
    ProductCategoryJpaEntity save(ProductCategoryJpaEntity category);
    Optional<ProductCategoryJpaEntity> findById(Long categoryId);
    List<ProductCategoryJpaEntity> findAll();
    List<ProductCategoryJpaEntity> findAllByIds(List<Long> categoryId);
    Optional<ProductCategoryJpaEntity> findByName(String name);
}