package app.inventory.app;

import module.enums.MovementType;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import domain.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementInApp {

    private final StockMovementRepository stockMovementRepo;

    /**
     * 재고 출고 처리
     */
    public StockMovementJpaEntity registerStockOut(Long inventoryId, Integer quantity, String description) {
        StockMovementJpaEntity movement = StockMovementJpaEntity.create(
            inventoryId,
            MovementType.OUT,
            quantity,
            description
        );
        movement.markAsStockOut(); // ← 기존의 비즈니스 로직 유지
        return stockMovementRepo.saveOutMovement(movement);
    }


    /**
     * 재고 입고 처리
     */
    public StockMovementJpaEntity registerStockIn(Long inventoryId, Integer quantity, String description) {
        StockMovementJpaEntity movement = StockMovementJpaEntity.create(
            inventoryId,
            MovementType.IN,
            quantity,
            description
        );
        movement.markAsStockIn(); // 기존 처리 유지
        return stockMovementRepo.saveInMovement(movement);
    }

    /**
     * 특정 상품의 재고 이동 이력 조회
     */
    @Transactional(readOnly = true)
    public List<StockMovementJpaEntity> getMovementHistory(Long productId) {
        return stockMovementRepo.findByProductId(productId);
    }


}
