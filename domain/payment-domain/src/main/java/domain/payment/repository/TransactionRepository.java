package domain.payment.repository;

import domain.payment.entity.TransactionJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository {
    TransactionJpaEntity save(TransactionJpaEntity entity);
    Optional<TransactionJpaEntity> findByPgOrderId(String orderId);
}