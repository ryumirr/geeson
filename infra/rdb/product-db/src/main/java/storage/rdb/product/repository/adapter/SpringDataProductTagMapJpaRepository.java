package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductTagMapJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductTagMapJpaRepository extends JpaRepository<ProductTagMapJpaEntity, Long> {
}