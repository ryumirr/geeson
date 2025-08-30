package storage.rdb.inventory.repository.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;

public interface SpringDataInventoryItemsJpaRepository  extends JpaRepository<InventoryItemsJpaEntity, Long> {
}
