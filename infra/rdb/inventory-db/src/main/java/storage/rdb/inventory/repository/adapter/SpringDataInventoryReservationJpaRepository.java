package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInventoryReservationJpaRepository
        extends JpaRepository<InventoryReservationJpaEntity, Long> {

    List<InventoryReservationJpaEntity> findByInventory_InventoryId(Long inventoryId);

}
