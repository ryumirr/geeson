package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.IdempotencyLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataIdempotencyLogJpaRepository
        extends JpaRepository<IdempotencyLogJpaEntity, String> {
}
