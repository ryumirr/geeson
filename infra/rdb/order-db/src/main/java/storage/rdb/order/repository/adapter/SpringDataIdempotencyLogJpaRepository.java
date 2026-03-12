package storage.rdb.order.repository.adapter;

import domain.order.entity.IdempotencyLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataIdempotencyLogJpaRepository
        extends JpaRepository<IdempotencyLogJpaEntity, String> {
}
