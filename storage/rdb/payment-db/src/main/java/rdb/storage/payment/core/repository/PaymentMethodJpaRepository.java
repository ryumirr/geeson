package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethodJpaRepository, Long> {
}

