package storage.rdb.product.repository;

import domain.product.domain.repository.ProductTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductTagMapJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductTagMapJpaRepository implements ProductTagMapRepository {
    private final SpringDataProductTagMapJpaRepository repository;
}