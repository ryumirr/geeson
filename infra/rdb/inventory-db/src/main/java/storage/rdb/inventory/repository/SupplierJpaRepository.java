package storage.rdb.inventory.repository;

import app.inventory.domain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataSupplierJpaRepository;

@Repository
@RequiredArgsConstructor
public class SupplierJpaRepository implements SupplierRepository {
    private final SpringDataSupplierJpaRepository repository;
}
