package app.inventory.app;

import module.enums.MovementType;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import domain.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.inventory.dto.AddStockMovementCommand;
import app.inventory.dto.StockMovementResult;
import app.inventory.port.in.AddStockMovementUseCase;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementOutApp implements AddStockMovementUseCase {

    private final StockMovementRepository stockMovementRepo;

    /**
     * 기존 출고 처리용 (엔티티 직접 저장)
     */
    public StockMovementJpaEntity registerStockOut(Long inventoryId,
                                                   Integer quantity,
                                                   String description,
                                                   String referenceId) {
        StockMovementJpaEntity entity = StockMovementJpaEntity.create(
                inventoryId,
                MovementType.OUT,
                quantity,
                description,
                referenceId
        );
        return stockMovementRepo.saveOutMovement(entity);
    }

    /**
     * UseCase 구현체: AddStockMovementCommand → Entity 변환 → 저장 → Result 반환
     */
    @Override
    public StockMovementResult add(AddStockMovementCommand command) {
        StockMovementJpaEntity entity = StockMovementJpaEntity.create(
                command.getInventoryId(),
                command.getMovementType(),
                command.getQuantity(),
                command.getDescription(),
                command.getReferenceId()
        );

        StockMovementJpaEntity saved = stockMovementRepo.saveOutMovement(entity);

        return new StockMovementResult(
                saved.getMovementId(),
                saved.getInventory().getInventoryId(),
                saved.getMovementType(),
                saved.getQuantity(),
                saved.getMovementDate(),
                saved.getDescription(),
                saved.getReferenceId()
        );
    }
}
