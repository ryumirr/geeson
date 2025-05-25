package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataWarehouseJpaRepository;

@Repository
@RequiredArgsConstructor
public class WarehouseJpaRepository implements WarehouseRepository {
    private final SpringDataWarehouseJpaRepository repository;
}
