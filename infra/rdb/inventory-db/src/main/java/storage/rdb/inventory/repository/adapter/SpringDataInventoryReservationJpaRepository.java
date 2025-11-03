package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInventoryReservationJpaRepository
        extends JpaRepository<InventoryReservationJpaEntity, Long> {

    // 특정 인벤토리 기준 예약 조회
    List<InventoryReservationJpaEntity> findByInventory_InventoryId(Long inventoryId);

    // 특정 주문 기준 예약 조회
    List<InventoryReservationJpaEntity> findByOrderId(Long orderId);
}
