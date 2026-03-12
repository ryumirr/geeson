package storage.rdb.inventory.repository.adapter;

import domain.inventory.domain.entity.OutboxJpaEntity;
import module.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataOutboxJpaRepository extends JpaRepository<OutboxJpaEntity, Long> {
    List<OutboxJpaEntity> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
