package storage.rdb.product.repository;

import app.product.domain.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductImageJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductImageJpaRepository implements ProductImageRepository {
    private final SpringDataProductImageJpaRepository repository;
}