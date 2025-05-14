package rdb.storage.inventory.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.InventoryJpaEntity;
import rdb.storage.inventory.core.entity.StockMovementJpaEntity;

import java.time.LocalDateTime;

public interface StockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
}
