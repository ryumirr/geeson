package storage.rdb.order.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryJpaRepository extends JpaRepository<OrderStatusHistoryJpaRepository, Long> {
}
