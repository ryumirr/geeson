package storage.rdb.inventory.repository;

import domain.inventory.domain.entity.OutboxJpaEntity;
import domain.inventory.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import module.enums.OutboxStatus;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataOutboxJpaRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxJpaRepository implements OutboxRepository {

    private final SpringDataOutboxJpaRepository repository;

    @Override
    public OutboxJpaEntity save(OutboxJpaEntity event) {
        return repository.save(event);
    }

    @Override
    public List<OutboxJpaEntity> findPendingEvents() {
        return repository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
    }
}
