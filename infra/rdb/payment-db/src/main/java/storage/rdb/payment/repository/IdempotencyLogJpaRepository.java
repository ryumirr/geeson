package storage.rdb.payment.repository;

import domain.payment.entity.IdempotencyLogJpaEntity;
import domain.payment.repository.IdempotencyLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataIdempotencyLogJpaRepository;

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
