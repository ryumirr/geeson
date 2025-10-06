package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataStockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
    /**
     * 특정 인벤토리의 이동 기록 조회
     */
    List<StockMovementJpaEntity> findByInventory_InventoryId(Long inventoryId);

    /**
     * referenceId 로 조회 (중복 방지용)
     */
    StockMovementJpaEntity findByReferenceId(String referenceId);

}
