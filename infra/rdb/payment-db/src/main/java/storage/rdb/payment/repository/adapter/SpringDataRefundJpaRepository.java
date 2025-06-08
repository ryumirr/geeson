package storage.rdb.payment.repository.adapter;

import domain.payment.entity.RefundJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRefundJpaRepository extends JpaRepository<RefundJpaEntity, Long> {
}
