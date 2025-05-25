package storage.rdb.order.repository;

import app.order.repository.OrderStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataOrderStatusHistoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class OrderStatusHistoryJpaRepository implements OrderStatusHistoryRepository {
    private final SpringDataOrderStatusHistoryJpaRepository repository;
}
