package storage.rdb.inventory.repository.adapter;

import app.inventory.domain.entity.SupplierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSupplierJpaRepository extends JpaRepository<SupplierJpaEntity, Long> {
}
