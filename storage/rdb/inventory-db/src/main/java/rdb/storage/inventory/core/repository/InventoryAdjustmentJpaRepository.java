package rdb.storage.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.InventoryAdjustmentJpaEntity;

public interface InventoryAdjustmentJpaRepository extends JpaRepository<InventoryAdjustmentJpaEntity, Long> {
}
