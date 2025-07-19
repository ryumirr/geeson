package domain.product.domain.repository;

import domain.product.domain.entity.BrandJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository {
    Optional<BrandJpaEntity> findById(Long id);
    BrandJpaEntity save(BrandJpaEntity brand);
}
