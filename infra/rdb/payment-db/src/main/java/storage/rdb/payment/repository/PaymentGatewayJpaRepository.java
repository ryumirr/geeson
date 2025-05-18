package storage.rdb.payment.repository;

import app.payment.domain.repository.PaymentGatewayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentGatewayJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentGatewayJpaRepository implements PaymentGatewayRepository {
    private final SpringDataPaymentGatewayJpaRepository repository;
}