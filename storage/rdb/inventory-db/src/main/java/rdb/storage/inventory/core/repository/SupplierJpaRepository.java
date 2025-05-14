package rdb.storage.inventory.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.SupplierJpaEntity;

import java.time.LocalDateTime;

public interface SupplierJpaRepository extends JpaRepository<SupplierJpaEntity, Long> {
}
