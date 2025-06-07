package storage.rdb.order.repository;

import domain.order.entity.ShipmentJpaEntity;
import domain.order.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataShipmentJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShipmentJpaRepository implements ShipmentRepository {
    private final SpringDataShipmentJpaRepository repository;

    public Optional<ShipmentJpaEntity> findByShipmentId(Long shipmentId) {
        return repository.findById(shipmentId);
    }
}
