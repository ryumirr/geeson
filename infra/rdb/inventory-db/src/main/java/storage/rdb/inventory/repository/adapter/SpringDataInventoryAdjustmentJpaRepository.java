package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryAdjustmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryAdjustmentJpaRepository extends JpaRepository<InventoryAdjustmentJpaEntity, Long> {
}
