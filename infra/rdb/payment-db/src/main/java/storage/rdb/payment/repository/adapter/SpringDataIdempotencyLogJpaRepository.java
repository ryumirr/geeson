package storage.rdb.payment.repository.adapter;

import domain.payment.entity.IdempotencyLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataIdempotencyLogJpaRepository
        extends JpaRepository<IdempotencyLogJpaEntity, String> {
}
