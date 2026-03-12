package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.BrandJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBrandJpaRepository extends JpaRepository<BrandJpaEntity, Long> {
}
