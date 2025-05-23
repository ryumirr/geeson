package storage.rdb.product.repository;

import app.product.domain.repository.ProductCategoryMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductCategoryMapJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductCategoryMapJpaRepository implements ProductCategoryMapRepository {
    private final SpringDataProductCategoryMapJpaRepository repository;
}