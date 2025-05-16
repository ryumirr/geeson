package storage.rdb.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaRepository, Long> {
}