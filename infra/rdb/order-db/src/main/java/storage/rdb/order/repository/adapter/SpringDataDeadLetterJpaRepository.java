package storage.rdb.order.repository.adapter;

import domain.order.domain.entity.DeadLetterMessageJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeadLetterJpaRepository extends JpaRepository<DeadLetterMessageJpaEntity, Long> {
    List<DeadLetterMessageJpaEntity> findByProcessedFalseAndNextRetryAtBefore(LocalDateTime now);
}
