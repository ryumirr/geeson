package storage.rdb.inventory.repository;

import domain.inventory.domain.entity.IdempotencyLogJpaEntity;
import domain.inventory.domain.repository.IdempotencyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataIdempotencyLogJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IdempotencyLogJpaRepository implements IdempotencyLogRepository {

    private final SpringDataIdempotencyLogJpaRepository repository;

    @Override
    public Optional<IdempotencyLogJpaEntity> findById(String idempotencyKey) {
        return repository.findById(idempotencyKey);
    }

    @Override
    public IdempotencyLogJpaEntity save(IdempotencyLogJpaEntity entity) {
        return repository.save(entity);
    }
}
