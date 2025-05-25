package storage.rdb.order.repository;

import app.order.repository.ProductOrderRepository;
import domain.order.domain.entity.ProductOrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataProductOrderJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductOrderJpaRepository implements ProductOrderRepository {
    private final SpringDataProductOrderJpaRepository repository;

    @Override
    public ProductOrderJpaEntity save(ProductOrderJpaEntity entity) {
        return repository.save(entity);
    }

}
