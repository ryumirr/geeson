package storage.rdb.inventory.repository.adapter;

import app.inventory.domain.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
}
