package storage.rdb.payment.repository;

import app.payment.repository.PaymentGatewayRepository;
import domain.payment.domain.entity.PaymentGatewayJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentGatewayJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentGatewayJpaRepository implements PaymentGatewayRepository {
    private final SpringDataPaymentGatewayJpaRepository repository;

    @Override
    public PaymentGatewayJpaEntity save(PaymentGatewayJpaEntity entity) {
        return repository.save(entity);
    }
}