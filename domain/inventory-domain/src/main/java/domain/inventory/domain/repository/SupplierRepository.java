package domain.inventory.domain.repository;

import domain.inventory.domain.entity.SupplierJpaEntity;
import java.util.Optional;

public interface SupplierRepository {
    Optional<SupplierJpaEntity> findById(Long id);
}
