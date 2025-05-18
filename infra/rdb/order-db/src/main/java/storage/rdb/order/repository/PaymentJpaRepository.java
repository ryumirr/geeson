package storage.rdb.order.repository;

import app.order.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataPaymentJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentJpaRepository implements PaymentRepository {
    private final SpringDataPaymentJpaRepository repository;
}