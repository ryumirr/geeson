package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductVariantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductVariantJpaRepository extends JpaRepository<ProductVariantJpaEntity, Long> {
}
