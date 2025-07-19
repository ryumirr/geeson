package storage.rdb.order.repository.adapter;

import domain.order.entity.ProductOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataProductOrderJpaRepository extends JpaRepository<ProductOrderJpaEntity, Long> {
    List<ProductOrderJpaEntity> findByCustomer_CustomerId(Long customerId);
}
