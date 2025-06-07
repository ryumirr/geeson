package storage.rdb.order.repository.adapter;

import domain.order.entity.OrderItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderItemJpaRepository extends JpaRepository<OrderItemJpaEntity, Long> {
}