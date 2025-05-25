package app.payment.repository;

import domain.payment.domain.entity.PaymentGatewayJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGatewayRepository  {
    PaymentGatewayJpaEntity save(PaymentGatewayJpaEntity entity);
}