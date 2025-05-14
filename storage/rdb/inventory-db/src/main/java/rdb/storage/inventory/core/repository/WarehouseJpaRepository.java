package rdb.storage.inventory.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.WarehouseJpaEntity;

import java.time.LocalDateTime;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, Long> {
}
