package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.InventoryJpaEntity;
import domain.inventory.domain.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataInventoryJpaRepository extends JpaRepository<InventoryJpaEntity, Long> {
    @Query("select i from InventoryJpaEntity i inner join i.product p where p.productId = :productId")
    List<InventoryJpaEntity> findAllByProductId(Long productId);
}
