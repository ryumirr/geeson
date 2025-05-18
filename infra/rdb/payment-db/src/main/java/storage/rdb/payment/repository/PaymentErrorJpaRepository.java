package storage.rdb.payment.repository;

import app.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentErrorJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentErrorJpaRepository implements PaymentRepository {
    private final SpringDataPaymentErrorJpaRepository repository;
}