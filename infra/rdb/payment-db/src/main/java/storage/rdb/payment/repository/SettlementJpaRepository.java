package storage.rdb.payment.repository;

import domain.payment.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataSettlementJpaRepository;

@Repository
@RequiredArgsConstructor
public class SettlementJpaRepository implements SettlementRepository {
    private final SpringDataSettlementJpaRepository repository;
}