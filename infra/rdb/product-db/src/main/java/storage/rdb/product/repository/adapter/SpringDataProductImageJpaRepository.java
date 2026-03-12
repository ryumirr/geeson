package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductImageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductImageJpaRepository extends JpaRepository<ProductImageJpaEntity, Long> {
}