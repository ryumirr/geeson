package storage.rdb.order.repository.adapter;

import domain.order.entity.OrderStatusHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderStatusHistoryJpaRepository extends JpaRepository<OrderStatusHistoryJpaEntity, Long> {
}
