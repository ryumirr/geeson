package storage.rdb.product.repository;

import domain.product.domain.entity.ProductPriceJpaEntity;
import domain.product.domain.repository.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductPriceJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductPriceJpaRepository implements ProductPriceRepository {
    private final SpringDataProductPriceJpaRepository repository;

    @Override
    public Optional<ProductPriceJpaEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ProductPriceJpaEntity save(ProductPriceJpaEntity entity) {
        return repository.save(entity);
    }
}
