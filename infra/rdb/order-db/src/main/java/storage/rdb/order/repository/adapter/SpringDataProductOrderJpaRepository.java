package storage.rdb.order.repository.adapter;

import domain.order.entity.ProductOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductOrderJpaRepository extends JpaRepository<ProductOrderJpaEntity, Long> {
}
