package storage.rdb.inventory.repository;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryJpaRepository implements InventoryRepository {
    private final SpringDataInventoryJpaRepository repository;

    @Override
    public List<InventoryJpaEntity> findByProductId(Long productId) {
        return repository.findAllByProductId(productId);
    }

    @Override
    public InventoryJpaEntity save(InventoryJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<InventoryJpaEntity> saveAll(List<InventoryJpaEntity> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<InventoryJpaEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InventoryJpaEntity> findAllByProductIdIn(List<Long> productIds) {
        return repository.findAllByProductIdIn(productIds);
    }

    @Override
    public Optional<InventoryJpaEntity> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        return repository.findByProductIdAndWarehouseId(productId, warehouseId);
    }

    @Override
    public List<InventoryJpaEntity> findAllByProductIdAndWarehouseIdIn(List<Object[]> pairs) {
        return repository.findAllByProductIdAndWarehouseIdIn(pairs);
    }
}
