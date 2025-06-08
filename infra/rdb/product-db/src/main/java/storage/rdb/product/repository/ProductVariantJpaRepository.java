package storage.rdb.product.repository;

import domain.product.domain.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductVariantJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductVariantJpaRepository implements ProductVariantRepository {
    private final SpringDataProductVariantJpaRepository repository;
}
