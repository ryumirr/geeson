package storage.rdb.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentGatewayJpaRepository extends JpaRepository<PaymentGatewayJpaRepository, Long> {
}