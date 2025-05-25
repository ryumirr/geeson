package storage.rdb.payment.repository;

import app.payment.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataRefundJpaRepository;

@Repository
@RequiredArgsConstructor
public class RefundJpaRepository implements RefundRepository {
    private final SpringDataRefundJpaRepository repository;
}
