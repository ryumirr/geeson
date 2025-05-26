package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryReservationJapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryReservationJpaRepository extends JpaRepository<InventoryReservationJapEntity, Long> {
}
