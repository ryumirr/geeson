package storage.rdb.payment.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.payment.core.entity.ReceiptJpaEntity;

public interface ReceiptJpaRepository extends JpaRepository<ReceiptJpaEntity, Long> {
}
