package app.order.repository;

import domain.order.domain.entity.DeadLetterMessageJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface DeadLetterRepository {
    List<DeadLetterMessageJpaEntity> findByProcessedFalseAndNextRetryAtBefore(LocalDateTime now);
    void save(DeadLetterMessageJpaEntity entity);
}