package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.PurchaseOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, Long> {
}
