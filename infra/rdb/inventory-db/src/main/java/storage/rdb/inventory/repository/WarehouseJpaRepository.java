package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataWarehouseJpaRepository;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WarehouseJpaRepository implements WarehouseRepository {
    private final SpringDataWarehouseJpaRepository jpa;

    @Override
    public WarehouseJpaEntity save(WarehouseJpaEntity entity) {
        return jpa.save(entity);
    }

    @Override
    public List<WarehouseJpaEntity> saveAll(List<WarehouseJpaEntity> entities) {
        return jpa.saveAll(entities);
    }

    @Override
    public List<WarehouseJpaEntity> findAll() {
        return jpa.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jpa.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return jpa.existsById(id);
    }
}