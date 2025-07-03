package storage.rdb.order.repository;

import domain.order.domain.entity.DeadLetterMessageJpaEntity;
import domain.order.repository.DeadLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataDeadLetterJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeadLetterJpaRepository implements DeadLetterRepository {

    private final SpringDataDeadLetterJpaRepository repository;

    @Override
    public List<DeadLetterMessageJpaEntity> findByProcessedFalseAndNextRetryAtBefore(LocalDateTime now) {
        return repository.findByProcessedFalseAndNextRetryAtBefore(now);
    }

    @Override
    public void save(DeadLetterMessageJpaEntity entity) {
        repository.save(entity);
    }
}