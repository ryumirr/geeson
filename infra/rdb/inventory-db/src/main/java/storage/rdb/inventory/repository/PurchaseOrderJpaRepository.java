package storage.rdb.inventory.repository;

import domain.inventory.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.inventory.repository.adapter.SpringDataPurchaseOrderJpaRepository;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {
    private final SpringDataPurchaseOrderJpaRepository repository;
}
