package storage.rdb.order.repository;

import app.order.repository.ShipmentRepository;
import domain.order.domain.entity.ShipmentJpaEntity;
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
