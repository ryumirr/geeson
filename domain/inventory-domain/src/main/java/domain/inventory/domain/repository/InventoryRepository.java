package domain.inventory.domain.repository;

import domain.inventory.domain.entity.InventoryJpaEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository {
    Optional<InventoryJpaEntity> findById(Long id);
    List<InventoryJpaEntity> findByProductId(Long productId);
    InventoryJpaEntity save(InventoryJpaEntity entity);
    // 단일 productId 조건 (여러 개)
    List<InventoryJpaEntity> findAllByProductIdIn(List<Long> productIds);

    // 단일 (productId + warehouseId) 조합
    Optional<InventoryJpaEntity> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    /**
     * 복수 (productId + warehouseId) 조합
     * // 단건 조회
            Optional<InventoryJpaEntity> inv =
                    inventoryRepository.findByProductIdAndWarehouseId(1L, 100L);

            // 복수 조회
            List<Object[]> keys = List.of(
                new Object[]{1L, 100L},
                new Object[]{2L, 200L}
            );
     * @param pairs
     * @return
     */
    @Query("""
        SELECT i FROM InventoryJpaEntity i
        WHERE (i.productId, i.warehouseId) IN :pairs
    """)
    List<InventoryJpaEntity> findAllByProductIdAndWarehouseIdIn(@Param("pairs") List<Object[]> pairs);
}
