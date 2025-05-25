package storage.rdb.order.repository;

import app.order.repository.CustomerRepository;
import domain.order.domain.entity.CustomerJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataCustomerJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerJpaRepository implements CustomerRepository {
    private final SpringDataCustomerJpaRepository repository;

    @Override
    public Optional<CustomerJpaEntity> findByCustomerId(Long customerId) {
        return repository.findById(customerId);
    }

    @Override
    public CustomerJpaEntity save(CustomerJpaEntity entity) {
        return repository.save(entity);
    }
}
