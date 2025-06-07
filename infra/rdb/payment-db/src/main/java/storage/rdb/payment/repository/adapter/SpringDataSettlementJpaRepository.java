package storage.rdb.payment.repository.adapter;

import domain.payment.entity.SettlementJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSettlementJpaRepository extends JpaRepository<SettlementJpaEntity, Long> {
}