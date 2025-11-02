package domain.inventory.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.inventory.domain.entity.PurchaseOrderJpaEntity;

@Repository
public interface PurchaseOrderRepository {
    PurchaseOrderJpaEntity save(PurchaseOrderJpaEntity entity);
    Optional<PurchaseOrderJpaEntity> findById(Long id);
    List<PurchaseOrderJpaEntity> findAllById(List<Long> ids);
}
