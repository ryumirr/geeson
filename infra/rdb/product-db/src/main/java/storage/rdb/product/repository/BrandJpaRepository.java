package storage.rdb.product.repository;

import app.product.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataBrandJpaRepository;

@Repository
@RequiredArgsConstructor
public class BrandJpaRepository implements BrandRepository {
    private final SpringDataBrandJpaRepository repository;
}
