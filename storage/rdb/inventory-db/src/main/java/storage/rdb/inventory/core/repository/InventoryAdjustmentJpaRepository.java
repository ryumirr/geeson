package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.InventoryAdjustmentJpaEntity;

public interface InventoryAdjustmentJpaRepository extends JpaRepository<InventoryAdjustmentJpaEntity, Long> {
}
