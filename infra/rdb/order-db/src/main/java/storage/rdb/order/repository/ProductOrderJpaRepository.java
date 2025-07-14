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
    public List<ProductOrderJpaEntity> getAllOrders(int page, int size) {
        return repository.findAll();
    }

    @Override
    public Optional<ProductOrderJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
}