package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.InventoryJpaEntity;

public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
}
