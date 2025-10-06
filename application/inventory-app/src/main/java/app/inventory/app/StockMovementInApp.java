package app.inventory.app;

import module.enums.MovementType;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import domain.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementInApp {

    private final StockMovementRepository stockMovementRepo;

    /**
     * 재고 출고 처리
     */
    public StockMovementJpaEntity registerStockOut(Long inventoryId, Integer quantity, String description, String reference_id) {
        StockMovementJpaEntity movement = StockMovementJpaEntity.create(
            inventoryId,
            MovementType.OUT,
            quantity,
            description,
            reference_id
        );
        movement.markAsStockOut(); // ← 기존의 비즈니스 로직 유지
        return stockMovementRepo.saveOutMovement(movement);
    }


    /**
     * 재고 입고 처리
     */
    public StockMovementJpaEntity registerStockIn(Long inventoryId, Integer quantity, String description, String reference_id) {
        StockMovementJpaEntity movement = StockMovementJpaEntity.create(
            inventoryId,
            MovementType.IN,
            quantity,
            description,
            reference_id
        );
        movement.markAsStockIn(); // 기존 처리 유지
        return stockMovementRepo.saveInMovement(movement);
    }
}
