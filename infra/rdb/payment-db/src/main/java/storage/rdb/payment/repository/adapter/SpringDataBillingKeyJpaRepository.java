package storage.rdb.payment.repository.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.payment.core.entity.BillingKeyJpaEntity;

public interface SpringDataBillingKeyJpaRepository extends JpaRepository<BillingKeyJpaEntity, Long> {
}