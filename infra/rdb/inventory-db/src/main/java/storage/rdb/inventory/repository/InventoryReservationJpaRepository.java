package storage.rdb.inventory.repository;

import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import domain.inventory.domain.repository.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryReservationJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryReservationJpaRepository implements InventoryReservationRepository {

    private final SpringDataInventoryReservationJpaRepository repository;

    @Override
    public List<InventoryReservationJpaEntity> findByInventory_InventoryId(Long inventoryId) {
        return repository.findByInventory_InventoryId(inventoryId);
    }

    @Override
    public InventoryReservationJpaEntity save(InventoryReservationJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<InventoryReservationJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
}