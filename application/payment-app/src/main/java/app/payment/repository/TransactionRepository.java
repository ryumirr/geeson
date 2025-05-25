package app.payment.repository;

import domain.payment.domain.entity.TransactionJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository {
    TransactionJpaEntity save(TransactionJpaEntity entity);
}