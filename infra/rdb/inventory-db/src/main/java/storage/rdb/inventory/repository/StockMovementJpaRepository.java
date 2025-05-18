package storage.rdb.inventory.repository;

import app.inventory.domain.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataStockMovementJpaRepository;

@Repository
@RequiredArgsConstructor
public class StockMovementJpaRepository implements StockMovementRepository {
    private final SpringDataStockMovementJpaRepository repository;
}
