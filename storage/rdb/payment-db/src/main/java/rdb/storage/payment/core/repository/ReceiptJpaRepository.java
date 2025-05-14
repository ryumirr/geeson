package rdb.storage.payment.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.payment.core.entity.ReceiptJpaEntity;

import java.time.LocalDateTime;

public interface ReceiptJpaRepository extends JpaRepository<ReceiptJpaEntity, Long> {
}
