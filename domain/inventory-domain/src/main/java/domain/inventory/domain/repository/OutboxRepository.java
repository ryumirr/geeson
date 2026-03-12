package domain.inventory.domain.repository;

import domain.inventory.domain.entity.OutboxJpaEntity;

import java.util.List;

public interface OutboxRepository {
    OutboxJpaEntity save(OutboxJpaEntity event);
    List<OutboxJpaEntity> findPendingEvents();
}
