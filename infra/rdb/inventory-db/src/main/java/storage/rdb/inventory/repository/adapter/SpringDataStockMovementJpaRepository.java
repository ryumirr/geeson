package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.StockMovementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataStockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
    @Query("select m from StockMovementJpaEntity m inner join m.inventory i inner join  i.product p where p.productId = :productId")
    List<StockMovementJpaEntity> findByProductId(Long productId);
}