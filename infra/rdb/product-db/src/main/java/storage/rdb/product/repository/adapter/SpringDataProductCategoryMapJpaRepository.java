package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductCategoryMapJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataProductCategoryMapJpaRepository extends JpaRepository<ProductCategoryMapJpaEntity, ProductCategoryMapJpaEntity.ProductCategoryMapId> {
    
    @Query("SELECT pcm FROM ProductCategoryMapJpaEntity pcm WHERE pcm.product.productId = :productId")
    List<ProductCategoryMapJpaEntity> findByProductId(@Param("productId") Long productId);
}