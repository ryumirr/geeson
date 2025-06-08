package domain.payment.repository;

import domain.payment.entity.PaymentGatewayJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGatewayRepository  {
    PaymentGatewayJpaEntity save(PaymentGatewayJpaEntity entity);
}