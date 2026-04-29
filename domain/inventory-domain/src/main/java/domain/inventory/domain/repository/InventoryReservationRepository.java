package domain.inventory.domain.repository;

import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import module.enums.ReservationStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReservationRepository {

    List<InventoryReservationJpaEntity> findByInventory_InventoryId(Long inventoryId);

    List<InventoryReservationJpaEntity> findByInventoryIdAndStatus(Long inventoryId, ReservationStatus status);

    Optional<InventoryReservationJpaEntity> findById(Long id);

    InventoryReservationJpaEntity save(InventoryReservationJpaEntity entity);
    List<InventoryReservationJpaEntity> findByOrderId(Long orderId);
}
