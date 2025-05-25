package storage.rdb.product.repository;

import domain.product.domain.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductOptionJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductOptionJpaRepository implements ProductOptionRepository {
    private final SpringDataProductOptionJpaRepository repository;
}