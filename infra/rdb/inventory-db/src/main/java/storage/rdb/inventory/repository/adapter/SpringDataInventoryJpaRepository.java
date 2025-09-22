package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.entity.ProductJpaEntity;
import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataInventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {

    @Query("select i from InventoryJpaEntity i where i.product.productId = :productId")
    List<InventoryJpaEntity> findAllByProductId(@Param("productId") Long productId);

    @Query("select i from InventoryJpaEntity i where i.product.productId in :productIds")
    List<InventoryJpaEntity> findAllByProductIdIn(@Param("productIds") List<Long> productIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from InventoryJpaEntity i " +
           "where i.product.productId = :productId and i.warehouseId = :warehouseId")
    Optional<InventoryJpaEntity> findByProductIdAndWarehouseId(@Param("productId") Long productId,
                                                               @Param("warehouseId") Long warehouseId);

    @Query("""
        select i from InventoryJpaEntity i
        where (i.product.productId, i.warehouseId) in :pairs
    """)
    List<InventoryJpaEntity> findAllByProductIdAndWarehouseIdIn(@Param("pairs") List<Object[]> pairs);
}
