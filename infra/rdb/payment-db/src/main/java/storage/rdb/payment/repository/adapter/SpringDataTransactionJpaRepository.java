package storage.rdb.payment.repository.adapter;

import domain.payment.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataTransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {
    Optional<TransactionJpaEntity> findByPgOrderId(String orderId);
}