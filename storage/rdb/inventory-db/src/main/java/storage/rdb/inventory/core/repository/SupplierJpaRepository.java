package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.SupplierJpaEntity;

public interface SupplierJpaRepository extends JpaRepository<SupplierJpaEntity, Long> {
}
