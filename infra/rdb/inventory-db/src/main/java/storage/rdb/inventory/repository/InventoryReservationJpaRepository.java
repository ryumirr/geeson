package storage.rdb.inventory.repository;

import app.inventory.domain.repository.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryReservationJpaRepository;

@Repository
@RequiredArgsConstructor
public class InventoryReservationJpaRepository implements InventoryReservationRepository {
    private final SpringDataInventoryReservationJpaRepository repository;
}
