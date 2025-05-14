package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.payment.core.entity.BillingKeyJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BillingKeyJpaRepository extends JpaRepository<BillingKeyJpaEntity, Long> {
}