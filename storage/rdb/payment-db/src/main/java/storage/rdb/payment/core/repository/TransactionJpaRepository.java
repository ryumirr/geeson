package storage.rdb.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaRepository, Long> {
}