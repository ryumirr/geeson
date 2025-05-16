package storage.rdb.order.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemJpaRepository, Long> {
}