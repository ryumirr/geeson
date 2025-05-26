package storage.rdb.payment.repository.adapter;

import domain.payment.domain.entity.BillingKeyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBillingKeyJpaRepository extends JpaRepository<BillingKeyJpaEntity, Long> {
}