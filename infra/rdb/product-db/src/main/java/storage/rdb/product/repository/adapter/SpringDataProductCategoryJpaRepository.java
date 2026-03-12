package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, Long> {
    Optional<ProductCategoryJpaEntity> findByName(String name);
}