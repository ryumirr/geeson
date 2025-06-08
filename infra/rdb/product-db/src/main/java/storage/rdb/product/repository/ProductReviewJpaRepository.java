package storage.rdb.product.repository;

import domain.product.domain.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.product.repository.adapter.SpringDataProductReviewJpaRepository;

@Repository
@RequiredArgsConstructor
public class ProductReviewJpaRepository implements ProductReviewRepository {
    private final SpringDataProductReviewJpaRepository repository;
}