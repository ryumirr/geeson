package domain.order.repository;

import domain.order.domain.entity.DeadLetterMessageJpaEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeadLetterRepository {
    List<DeadLetterMessageJpaEntity> findByProcessedFalseAndNextRetryAtBefore(LocalDateTime now);
    void save(DeadLetterMessageJpaEntity entity);
}