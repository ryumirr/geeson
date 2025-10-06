package app.inventory.port.in;

import domain.inventory.domain.entity.StockMovementJpaEntity;

import java.util.List;

public interface GetStockMovementsByInventoryUseCase {
    /**
     * 특정 inventoryId의 이동 내역 조회
     */
    List<StockMovementJpaEntity> findByInventory_InventoryId(Long inventoryId);
}
