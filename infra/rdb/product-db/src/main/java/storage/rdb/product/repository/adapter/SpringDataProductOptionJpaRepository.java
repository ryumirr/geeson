package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductOptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductOptionJpaRepository extends JpaRepository<ProductOptionJpaEntity, Long> {
}