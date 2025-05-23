package storage.rdb.order.repository.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.order.core.entity.CustomerJpaEntity;

public interface SpringDataCustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
