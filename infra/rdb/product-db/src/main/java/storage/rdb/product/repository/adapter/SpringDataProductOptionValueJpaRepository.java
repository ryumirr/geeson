package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductOptionValueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductOptionValueJpaRepository extends JpaRepository<ProductOptionValueJpaEntity, Long> {
}