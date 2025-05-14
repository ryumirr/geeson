package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PaymentErrorJpaRepository extends JpaRepository<PaymentErrorJpaRepository, Long> {
}