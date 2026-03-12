package storage.rdb.product.repository;

import domain.product.domain.entity.BrandJpaEntity;
import domain.product.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataBrandJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BrandJpaRepository implements BrandRepository {
    private final SpringDataBrandJpaRepository repository;

    @Override
    public Optional<BrandJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public BrandJpaEntity save(BrandJpaEntity brand) {
        return repository.save(brand);
    }
}
