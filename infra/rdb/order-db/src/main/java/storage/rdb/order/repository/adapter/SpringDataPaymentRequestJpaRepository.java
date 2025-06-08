package storage.rdb.order.repository.adapter;

import domain.order.entity.PaymentRequestJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentRequestJpaRepository extends JpaRepository<PaymentRequestJpaEntity, Long> {
}