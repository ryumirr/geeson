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
public class StockMovementOutApp {

    private final StockMovementRepository stockMovementRepo;

    public StockMovementJpaEntity registerStockOut(Long inventoryId, Integer quantity, String description) {
        StockMovementJpaEntity entity = StockMovementJpaEntity.create(
                inventoryId,
                MovementType.OUT,
                quantity,
                description);
        return stockMovementRepo.saveOutMovement(entity);
    }
}