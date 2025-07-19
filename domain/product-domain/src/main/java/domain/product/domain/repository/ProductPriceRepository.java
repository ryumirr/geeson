package domain.product.domain.repository;

import domain.product.domain.entity.ProductPriceJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPriceRepository {
    Optional<ProductPriceJpaEntity> findById(Long id);
    ProductPriceJpaEntity save(ProductPriceJpaEntity entity);
}
