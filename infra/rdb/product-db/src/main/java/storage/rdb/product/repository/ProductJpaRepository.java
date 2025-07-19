package storage.rdb.product.repository;

import domain.product.domain.entity.ProductJpaEntity;
import domain.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class ProductJpaRepository implements ProductRepository {
    private final SpringDataProductJpaRepository repository;

    @Override
    public ProductJpaEntity findBySku(String sku) {
        return repository.findBySku(sku);
    }

    @Override
    public ProductJpaEntity save(ProductJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<ProductJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
}