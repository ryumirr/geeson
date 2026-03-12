package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductPriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductPriceJpaRepository extends JpaRepository<ProductPriceJpaEntity, Long> {
}
