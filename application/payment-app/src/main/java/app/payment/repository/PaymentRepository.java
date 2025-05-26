package app.payment.repository;

import domain.payment.domain.entity.PaymentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository  {
    PaymentJpaEntity save(PaymentJpaEntity entity);
    Optional<PaymentJpaEntity> findByOrderId(Long orderId);
}