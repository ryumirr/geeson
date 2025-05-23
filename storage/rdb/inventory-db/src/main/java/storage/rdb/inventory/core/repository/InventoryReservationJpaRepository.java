package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.InventoryReservationJapEntity;

public interface InventoryReservationJpaRepository extends JpaRepository<InventoryReservationJapEntity, Long> {
}
