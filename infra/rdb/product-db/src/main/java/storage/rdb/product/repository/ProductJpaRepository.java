package storage.rdb.product.repository;

import app.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductJpaRepository implements ProductRepository {
    private final SpringDataProductJpaRepository repository;
}