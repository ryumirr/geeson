package storage.rdb.payment.repository;

import app.payment.repository.TransactionRepository;
import domain.payment.domain.entity.TransactionJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataTransactionJpaRepository;

@Repository
@RequiredArgsConstructor
public class TransactionJpaRepository implements TransactionRepository {
    private final SpringDataTransactionJpaRepository repository;

    @Override
    public TransactionJpaEntity save(TransactionJpaEntity entity) {
        return repository.save(entity);
    }
}