package domain.inventory.domain.repository;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository {
    /**
     * 재고 OUT 저장
     */
    StockMovementJpaEntity saveOutMovement(StockMovementJpaEntity movement);

    /**
     * 재고 IN 저장
     */
    StockMovementJpaEntity saveInMovement(StockMovementJpaEntity movement);

    /**
     * 상품별 이동 기록 조회
     */
    List<StockMovementJpaEntity> findByProductId(Long productId);
}