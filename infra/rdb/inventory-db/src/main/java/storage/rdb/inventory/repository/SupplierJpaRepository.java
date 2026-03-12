package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataSupplierJpaRepository;
import domain.inventory.domain.entity.SupplierJpaEntity;

@Repository
@RequiredArgsConstructor
public class SupplierJpaRepository implements SupplierRepository {
    private final SpringDataSupplierJpaRepository repository;
    @Override
    public Optional<SupplierJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
}
