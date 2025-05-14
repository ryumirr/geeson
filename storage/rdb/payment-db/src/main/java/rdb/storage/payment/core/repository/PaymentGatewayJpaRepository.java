package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentGatewayJpaRepository extends JpaRepository<PaymentGatewayJpaRepository, Long> {
}