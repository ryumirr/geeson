package rdb.storage.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.inventory.core.entity.InventoryAuditLogJpaEntity;

public interface InventoryAuditLogJpaRepository extends JpaRepository<InventoryAuditLogJpaEntity, Long> {
}
