package domain.order.repository;

import domain.order.entity.OrderItemJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository {
    List<OrderItemJpaEntity> findAllByOrderId(List<Long> orderId);
    List<OrderItemJpaEntity> saveAll(List<OrderItemJpaEntity> items);
}