package domain.inventory.domain.repository;

import domain.inventory.domain.entity.InventoryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository {
    List<InventoryJpaEntity> findByProductId(Long productId);
    
    /**
     * Save an inventory entity
     * @param entity The inventory entity to save
     * @return The saved inventory entity
     */
    InventoryJpaEntity save(InventoryJpaEntity entity);
}
