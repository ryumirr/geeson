package storage.rdb.order.repository;

import app.order.domain.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataOrderItemJpaRepository;

@Repository
@RequiredArgsConstructor
public class OrderItemJpaRepository implements OrderItemRepository {
    private final SpringDataOrderItemJpaRepository repository;
}