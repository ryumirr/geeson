package storage.rdb.order.repository;

import app.order.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataCustomerJpaRepository;

@Repository
@RequiredArgsConstructor
public class CustomerJpaRepository implements CustomerRepository {
    private final SpringDataCustomerJpaRepository repository;
}
