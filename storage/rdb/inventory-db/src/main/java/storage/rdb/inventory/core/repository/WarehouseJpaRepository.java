package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.WarehouseJpaEntity;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, Long> {
}
