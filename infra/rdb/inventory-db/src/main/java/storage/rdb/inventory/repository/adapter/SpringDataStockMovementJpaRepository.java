package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataStockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
    List<StockMovementJpaEntity> findByInventory_ProductId(Long productId);
}