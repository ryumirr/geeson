package storage.rdb.inventory.repository;

import app.inventory.domain.entity.InventoryAuditLogJpaEntity;
import app.inventory.domain.repository.InventoryAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryAuditLogJpaRepository;

@Repository
@RequiredArgsConstructor
public class InventoryAuditLogJpaRepository implements InventoryAuditLogRepository {
    private final SpringDataInventoryAuditLogJpaRepository repository;
}
