package storage.rdb.inventory.repository.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;

import java.util.Optional;

public interface SpringDataInventoryItemsJpaRepository extends JpaRepository<InventoryItemsJpaEntity, Long> {
    Optional<InventoryItemsJpaEntity> findBySerialNumber(String serialNumber);
}
