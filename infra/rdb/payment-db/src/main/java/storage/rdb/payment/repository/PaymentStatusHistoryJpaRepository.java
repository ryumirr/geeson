package storage.rdb.payment.repository;

import app.payment.repository.PaymentStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataPaymentStatusHistoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class PaymentStatusHistoryJpaRepository implements PaymentStatusHistoryRepository {
    private final SpringDataPaymentStatusHistoryJpaRepository repository;
}
