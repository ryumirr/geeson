package storage.rdb.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.payment.core.entity.BillingKeyJpaEntity;

public interface BillingKeyJpaRepository extends JpaRepository<BillingKeyJpaEntity, Long> {
}