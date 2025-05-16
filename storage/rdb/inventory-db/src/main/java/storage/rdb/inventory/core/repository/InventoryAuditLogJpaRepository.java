package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.InventoryAuditLogJpaEntity;

public interface InventoryAuditLogJpaRepository extends JpaRepository<InventoryAuditLogJpaEntity, Long> {
}
