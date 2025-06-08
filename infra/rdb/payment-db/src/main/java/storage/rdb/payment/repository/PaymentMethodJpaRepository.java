package storage.rdb.payment.repository;

import domain.payment.entity.PaymentMethodJpaEntity;
import domain.payment.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentMethodJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentMethodJpaRepository implements PaymentMethodRepository {
    private final SpringDataPaymentMethodJpaRepository repository;

    public PaymentMethodJpaEntity save(PaymentMethodJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<PaymentMethodJpaEntity> findByPaymentMethodId(Long paymentMethodId) {
        return repository.findById(paymentMethodId);
    }

    @Override
    public List<PaymentMethodJpaEntity> findAllByCustomerId(Long customerId) {
        return repository.findAllByCustomerId(customerId);
    }
}

