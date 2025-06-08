package domain.order.repository;

import domain.order.entity.CustomerJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository {
    Optional<CustomerJpaEntity> findByCustomerId(Long customerId);
    CustomerJpaEntity save(CustomerJpaEntity entity);
}
