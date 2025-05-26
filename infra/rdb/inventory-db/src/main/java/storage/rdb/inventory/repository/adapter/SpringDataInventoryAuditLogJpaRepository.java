package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryAuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryAuditLogJpaRepository extends JpaRepository<InventoryAuditLogJpaEntity, Long> {
}
