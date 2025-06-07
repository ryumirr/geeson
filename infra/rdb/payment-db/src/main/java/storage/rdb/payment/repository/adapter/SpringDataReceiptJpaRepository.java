package storage.rdb.payment.repository.adapter;

import domain.payment.entity.ReceiptJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReceiptJpaRepository extends JpaRepository<ReceiptJpaEntity, Long> {
}
