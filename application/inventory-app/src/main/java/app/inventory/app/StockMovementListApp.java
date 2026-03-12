package app.inventory.app;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import domain.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.inventory.port.in.GetStockMovementByReferenceUseCase;
import app.inventory.port.in.GetStockMovementsByInventoryUseCase;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementListApp implements GetStockMovementsByInventoryUseCase, GetStockMovementByReferenceUseCase {
    private final StockMovementRepository stockMovementRepo;

    @Override
    public List<StockMovementJpaEntity> findByInventory_InventoryId(Long inventoryId) {
        return stockMovementRepo.findByInventory_InventoryId(inventoryId);
    }

    @Override
    public StockMovementJpaEntity findByReferenceId(String referenceId) {
        return stockMovementRepo.findByReferenceId(referenceId);
    }
}
