package storage.rdb.inventory.repository;

import app.inventory.domain.repository.InventoryAdjustmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryAdjustmentJpaRepository;

@Repository
@RequiredArgsConstructor
public class InventoryAdjustmentJpaRepository implements InventoryAdjustmentRepository {
    private final SpringDataInventoryAdjustmentJpaRepository repository;
}
