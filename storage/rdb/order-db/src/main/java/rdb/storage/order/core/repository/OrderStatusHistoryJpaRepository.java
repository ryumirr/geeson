package rdb.storage.order.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OrderStatusHistoryJpaRepository extends JpaRepository<OrderStatusHistoryJpaRepository, Long> {
}
