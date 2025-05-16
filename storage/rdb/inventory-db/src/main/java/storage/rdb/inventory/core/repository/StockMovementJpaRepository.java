package storage.rdb.inventory.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.inventory.core.entity.StockMovementJpaEntity;

public interface StockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
}
