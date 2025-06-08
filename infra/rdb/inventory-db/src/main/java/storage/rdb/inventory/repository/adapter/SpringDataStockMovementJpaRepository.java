package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataStockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
}
