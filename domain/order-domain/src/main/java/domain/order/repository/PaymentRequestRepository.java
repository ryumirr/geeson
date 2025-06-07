package domain.order.repository;

import domain.order.entity.PaymentRequestJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository {
    PaymentRequestJpaEntity save(PaymentRequestJpaEntity entity);
}