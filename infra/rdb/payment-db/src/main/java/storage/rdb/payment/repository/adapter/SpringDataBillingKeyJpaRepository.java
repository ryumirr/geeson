package storage.rdb.payment.repository.adapter;

import domain.payment.entity.BillingKeyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBillingKeyJpaRepository extends JpaRepository<BillingKeyJpaEntity, Long> {
}