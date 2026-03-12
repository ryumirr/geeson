package domain.order.repository;

import domain.order.entity.IdempotencyLogJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdempotencyLogRepository {

    Optional<IdempotencyLogJpaEntity> findById(String idempotencyKey);

    IdempotencyLogJpaEntity save(IdempotencyLogJpaEntity entity);
}
