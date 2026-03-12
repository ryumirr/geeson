package storage.rdb.inventory.repository;

import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.WarehouseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WarehouseJpaRepository implements WarehouseRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public WarehouseJpaEntity save(WarehouseJpaEntity entity) {
        if (entity.getWarehouseId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    @Override
    public List<WarehouseJpaEntity> saveAll(List<WarehouseJpaEntity> entities) {
        for (WarehouseJpaEntity e : entities) {
            save(e);
        }
        return entities;
    }

    @Override
    public List<WarehouseJpaEntity> findAll() {
        return em.createQuery("SELECT w FROM WarehouseJpaEntity w", WarehouseJpaEntity.class)
                .getResultList();
    }

    @Override
    public Optional<WarehouseJpaEntity> findById(Long id) { // ✅ 추가
        WarehouseJpaEntity entity = em.find(WarehouseJpaEntity.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM WarehouseJpaEntity").executeUpdate();
    }

    @Override
    public boolean existsById(Long id) {
        Long count = em.createQuery(
                        "SELECT COUNT(w) FROM WarehouseJpaEntity w WHERE w.warehouseId = :id",
                        Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
