package storage.rdb.payment.repository;

import app.payment.domain.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataReceiptJpaRepository;

@Repository
@RequiredArgsConstructor
public class ReceiptJpaRepository implements ReceiptRepository {
    private final SpringDataReceiptJpaRepository repository;
}
