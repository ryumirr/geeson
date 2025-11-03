package storage.rdb.payment.repository;

import domain.payment.entity.TransactionJpaEntity;
import domain.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataTransactionJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionJpaRepository implements TransactionRepository {
    private final SpringDataTransactionJpaRepository repository;

    @Override
    public TransactionJpaEntity save(TransactionJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<TransactionJpaEntity> findByPgOrderId(String orderId) {
        return repository.findByPgOrderId(orderId);
    }
}