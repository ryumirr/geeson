package storage.rdb.payment.repository;

import app.payment.repository.BillingKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.payment.repository.adapter.SpringDataBillingKeyJpaRepository;

@Repository
@RequiredArgsConstructor
public class BillingKeyJpaRepository implements BillingKeyRepository {
    private final SpringDataBillingKeyJpaRepository repository;
}