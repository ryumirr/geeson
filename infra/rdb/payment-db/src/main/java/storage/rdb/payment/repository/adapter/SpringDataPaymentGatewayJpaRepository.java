package storage.rdb.payment.repository.adapter;

import domain.payment.domain.entity.PaymentGatewayJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentGatewayJpaRepository extends JpaRepository<PaymentGatewayJpaEntity, Long> {
}