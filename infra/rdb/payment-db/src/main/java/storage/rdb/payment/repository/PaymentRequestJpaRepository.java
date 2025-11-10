package storage.rdb.payment.repository;

import domain.payment.entity.PaymentJpaEntity;
import domain.payment.repository.PaymentRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentRequestJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentRequestJpaRepository implements PaymentRequestRepository {
    private final SpringDataPaymentRequestJpaRepository repository;

    @Override
    public PaymentJpaEntity save(PaymentJpaEntity entity) {
        return repository.save(entity);
    }
}