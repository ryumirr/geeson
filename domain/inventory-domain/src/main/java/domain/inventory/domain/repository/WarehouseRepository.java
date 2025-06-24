package domain.inventory.domain.repository;

import domain.inventory.domain.entity.WarehouseJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository {
    WarehouseJpaEntity save(WarehouseJpaEntity entity);
    List<WarehouseJpaEntity> saveAll(List<WarehouseJpaEntity> entities);
    List<WarehouseJpaEntity> findAll();
    void deleteById(Long id);
    void deleteAll();
    boolean existsById(Long id);
}