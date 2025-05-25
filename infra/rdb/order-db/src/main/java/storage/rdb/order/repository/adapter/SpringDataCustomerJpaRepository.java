package storage.rdb.order.repository.adapter;

import domain.order.domain.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
