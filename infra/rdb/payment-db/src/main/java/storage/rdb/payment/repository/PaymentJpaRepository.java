package storage.rdb.payment.repository;

import app.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepository implements PaymentRepository {
    private final SpringDataPaymentJpaRepository repository;
}