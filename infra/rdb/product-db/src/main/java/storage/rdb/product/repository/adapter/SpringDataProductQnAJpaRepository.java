package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductQnAJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductQnAJpaRepository extends JpaRepository<ProductQnAJpaEntity, Long> {
}
