package rdb.storage.inventory.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.PurchaseOrderJpaEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, Long> {
}
