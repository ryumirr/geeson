package storage.rdb.order.repository;

import domain.order.entity.OrderItemJpaEntity;
import domain.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataOrderItemJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemJpaRepository implements OrderItemRepository {
    private final SpringDataOrderItemJpaRepository repository;

    @Override
    public List<OrderItemJpaEntity> findAllByOrderId(List<Long> orderId) {
        return repository.findAllById( orderId);
    }

    @Override
    public List<OrderItemJpaEntity> saveAll(List<OrderItemJpaEntity> items) {
        return repository.saveAll(items);
    }
}