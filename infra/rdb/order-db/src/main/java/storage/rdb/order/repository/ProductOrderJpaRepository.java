package storage.rdb.order.repository;

import app.order.domain.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataProductOrderJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductOrderJpaRepository implements ProductOrderRepository {
    private final SpringDataProductOrderJpaRepository repository;
}
