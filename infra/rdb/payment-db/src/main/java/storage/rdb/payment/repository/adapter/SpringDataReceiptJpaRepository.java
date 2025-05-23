package storage.rdb.payment.repository.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.payment.core.entity.ReceiptJpaEntity;

public interface SpringDataReceiptJpaRepository extends JpaRepository<ReceiptJpaEntity, Long> {
}
