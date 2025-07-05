package domain.inventory.domain.repository;

import domain.inventory.domain.entity.InventoryReservationJpaEntity;

import java.util.List;
import java.util.Optional;

public interface InventoryReservationRepository {

    List<InventoryReservationJpaEntity> findByInventory_InventoryId(Long inventoryId);

    Optional<InventoryReservationJpaEntity> findById(Long id);

    InventoryReservationJpaEntity save(InventoryReservationJpaEntity entity);
}
