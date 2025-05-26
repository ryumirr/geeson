package app.order.repository;

import domain.order.domain.entity.PaymentRequestJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository {
    PaymentRequestJpaEntity save(PaymentRequestJpaEntity entity);
}