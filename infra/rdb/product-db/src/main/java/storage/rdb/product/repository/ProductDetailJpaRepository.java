package storage.rdb.product.repository;

import app.product.domain.repository.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductDetailJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductDetailJpaRepository implements ProductDetailRepository {
    private final SpringDataProductDetailJpaRepository repository;
}
