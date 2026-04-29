package storage.rdb.order.repository;

import domain.order.entity.ProductOrderJpaEntity;
import domain.order.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataProductOrderJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductOrderJpaRepository implements ProductOrderRepository {
    private final SpringDataProductOrderJpaRepository repository;

    @Override
    public ProductOrderJpaEntity save(ProductOrderJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<ProductOrderJpaEntity> getAllOrders(int page, int size, String status) {
        if (status != null) {
            return repository.findByStatus(status);
        }

        return repository.findAll();
    }

    @Override
    public Optional<ProductOrderJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public List<ProductOrderJpaEntity> findByCustomerId(Long customerId, String status) {
        if (status != null) {
            return repository.findByCustomer_CustomerIdAndStatus(customerId, status);
        }

        return repository.findByCustomer_CustomerId(customerId);
    }
}
