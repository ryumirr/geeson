package app.inventory;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import domain.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementListApp {
    private final StockMovementRepository stockMovementRepo;

    /**
     * 특정 상품의 재고 이동 이력 조회
     * @param productId
     * @return List<StockMovementJpaEntity>
     */
    @Transactional(readOnly = true)
    public List<StockMovementJpaEntity> getMovementHistory(Long productId) {
        return stockMovementRepo.findByProductId(productId);
    }
}