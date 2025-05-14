package rdb.storage.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.InventoryJpaEntity;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
}
