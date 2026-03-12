package app.inventory.port.in;

import domain.inventory.domain.entity.StockMovementJpaEntity;

public interface GetStockMovementByReferenceUseCase {
    /**
     * referenceId 기준으로 이동 내역 단건 조회
     */
    StockMovementJpaEntity findByReferenceId(String referenceId);
}
