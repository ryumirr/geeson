package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.StockMovementRepository;
import domain.inventory.domain.entity.StockMovementJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataStockMovementJpaRepository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockMovementJpaRepository implements StockMovementRepository {
    private final SpringDataStockMovementJpaRepository repository;

    @Override
    public StockMovementJpaEntity saveOutMovement(StockMovementJpaEntity movement) {
        movement.markAsStockOut();
        return repository.save(movement);
    }

    @Override
    public StockMovementJpaEntity saveInMovement(StockMovementJpaEntity movement) {
         movement.markAsStockIn();
        return repository.save(movement);
    }

    @Override
    public List<StockMovementJpaEntity> findByProductId(Long productId) {
        return repository.findByProductId(productId);
    }
}