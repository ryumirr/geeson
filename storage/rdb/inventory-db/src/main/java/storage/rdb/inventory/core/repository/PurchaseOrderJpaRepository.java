package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.PurchaseOrderJpaEntity;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, Long> {
}
