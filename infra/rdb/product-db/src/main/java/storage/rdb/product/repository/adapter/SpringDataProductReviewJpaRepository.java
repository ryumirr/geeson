package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.ProductReviewJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductReviewJpaRepository extends JpaRepository<ProductReviewJpaEntity, Long> {
}