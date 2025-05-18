package storage.rdb.inventory.repository.adapter;

import app.inventory.domain.entity.InventoryReservationJapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryReservationJpaRepository extends JpaRepository<InventoryReservationJapEntity, Long> {
}
