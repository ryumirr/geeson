package domain.payment.repository;

import domain.payment.entity.TransactionJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository {
    TransactionJpaEntity save(TransactionJpaEntity entity);
}