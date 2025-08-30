package domain.inventory.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.entity.InventoryJpaEntity;

@Repository
public interface InventoryItemsRepository {
    InventoryItemsJpaEntity save(InventoryItemsJpaEntity entity);
    Optional<InventoryItemsJpaEntity> findById(Long id);
}
