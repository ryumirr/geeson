package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataPurchaseOrderJpaRepository;
import domain.inventory.domain.entity.PurchaseOrderJpaEntity;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {
    private final SpringDataPurchaseOrderJpaRepository repository;
    @Override
    public List<PurchaseOrderJpaEntity> findAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }
    @Override
    public java.util.Optional<PurchaseOrderJpaEntity> findById(Long id) {
        return repository.findById(id);
    }
    @Override
    public PurchaseOrderJpaEntity save(PurchaseOrderJpaEntity entity) {
        return repository.save(entity);
    }
}
