package storage.rdb.product.repository;

import domain.product.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductCategoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductCategoryJpaRepository implements ProductCategoryRepository {
    private final SpringDataProductCategoryJpaRepository repository;
}