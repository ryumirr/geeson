package storage.rdb.inventory.repository.adapter;

import app.inventory.domain.entity.InventoryAdjustmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryAdjustmentJpaRepository extends JpaRepository<InventoryAdjustmentJpaEntity, Long> {
}
