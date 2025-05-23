package storage.rdb.order.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.order.core.entity.CustomerJpaEntity;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
