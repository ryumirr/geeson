package rdb.storage.inventory.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.InventoryJpaEntity;
import rdb.storage.inventory.core.entity.InventoryReservationJapEntity;

import java.time.LocalDateTime;

public interface InventoryReservationJpaRepository extends JpaRepository<InventoryReservationJapEntity, Long> {
}
