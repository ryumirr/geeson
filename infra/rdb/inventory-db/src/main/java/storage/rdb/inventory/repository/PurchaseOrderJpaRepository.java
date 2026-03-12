package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataPurchaseOrderJpaRepository;
import domain.inventory.domain.entity.PurchaseOrderJpaEntity;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {
    private final SpringDataPurchaseOrderJpaRepository repository;
    @PersistenceContext
    private EntityManager em;

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

    @Override
    public List<PurchaseOrderJpaEntity> findByWarehouse_WarehouseId(long warehouseId) {
        return em.createQuery(
                "SELECT p FROM PurchaseOrderJpaEntity p WHERE p.warehouse.warehouseId = :warehouseId",
                PurchaseOrderJpaEntity.class)
            .setParameter("warehouseId", warehouseId)
            .getResultList();
    }

    @Override
    public Optional<PurchaseOrderJpaEntity> findBySupplier_SupplierIdAndWarehouse_WarehouseId(long supplierId, long warehouseId) {
        return em.createQuery(
                "SELECT p FROM PurchaseOrderJpaEntity p WHERE p.supplier.supplierId = :supplierId AND p.warehouse.warehouseId = :warehouseId",
                PurchaseOrderJpaEntity.class)
            .setParameter("supplierId", supplierId)
            .setParameter("warehouseId", warehouseId)
            .getResultStream()
            .findFirst();
    }

    @Override
    public List<PurchaseOrderJpaEntity> findBySupplier_SupplierId(long supplierId) {
        return em.createQuery(
                "SELECT p FROM PurchaseOrderJpaEntity p WHERE p.supplier.supplierId = :supplierId",
                PurchaseOrderJpaEntity.class)
            .setParameter("supplierId", supplierId)
            .getResultList();
    }

    @Override
    public List<PurchaseOrderJpaEntity> findAll() {
        return repository.findAll();
    }
}
