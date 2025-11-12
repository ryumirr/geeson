package storage.rdb.payment.repository.adapter;

import domain.payment.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentRequestJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
}