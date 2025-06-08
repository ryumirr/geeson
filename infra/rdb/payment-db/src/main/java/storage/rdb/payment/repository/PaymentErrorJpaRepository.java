package storage.rdb.payment.repository;

import domain.payment.repository.PaymentErrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentErrorJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentErrorJpaRepository implements PaymentErrorRepository {
    private final SpringDataPaymentErrorJpaRepository repository;
}