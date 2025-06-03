package app.order.repository;

import domain.order.domain.entity.ProductOrderJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository  {
    ProductOrderJpaEntity save(ProductOrderJpaEntity entity);
    List<ProductOrderJpaEntity> getAllOrders(int page, int size);
}
