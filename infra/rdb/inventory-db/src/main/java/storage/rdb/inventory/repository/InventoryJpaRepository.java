package storage.rdb.inventory.repository;

import app.inventory.domain.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryJpaRepository;

@Repository
@RequiredArgsConstructor
public class InventoryJpaRepository implements InventoryRepository {
    private final SpringDataInventoryJpaRepository repository;
}
