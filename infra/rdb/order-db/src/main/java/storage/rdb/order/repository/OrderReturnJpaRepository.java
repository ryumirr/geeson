package storage.rdb.order.repository;

import domain.order.repository.OrderReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataOrderReturnJpaRepository;

@Repository
@RequiredArgsConstructor
public class OrderReturnJpaRepository implements OrderReturnRepository {
    private final SpringDataOrderReturnJpaRepository repository;
}