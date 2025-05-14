package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaRepository, Long> {
}