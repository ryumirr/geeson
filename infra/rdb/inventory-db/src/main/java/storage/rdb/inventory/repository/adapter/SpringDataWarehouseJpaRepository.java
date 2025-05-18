package storage.rdb.inventory.repository.adapter;

import app.inventory.domain.entity.WarehouseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataWarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, Long> {
}
