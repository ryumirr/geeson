package domain.payment.repository;

import domain.payment.entity.PaymentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository  {
    PaymentJpaEntity save(PaymentJpaEntity entity);
    Optional<PaymentJpaEntity> findByOrderId(String orderId);
    List<PaymentJpaEntity> findByCustomerId(Long customerId);
}