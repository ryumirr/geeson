package domain.order.repository;

import domain.order.entity.ProductOrderJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository {
    ProductOrderJpaEntity save(ProductOrderJpaEntity entity);

    List<ProductOrderJpaEntity> getAllOrders(int page, int size);

    Optional<ProductOrderJpaEntity> findById(Long id);
    
    List<ProductOrderJpaEntity> findByCustomerId(Long customerId);
}
