package storage.rdb.payment.repository.adapter;

import domain.payment.entity.PaymentErrorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentErrorJpaRepository extends JpaRepository<PaymentErrorJpaEntity, Long> {
}