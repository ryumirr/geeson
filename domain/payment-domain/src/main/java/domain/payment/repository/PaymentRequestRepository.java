package domain.payment.repository;

import domain.payment.entity.PaymentJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository {
    PaymentJpaEntity save(PaymentJpaEntity entity);
}