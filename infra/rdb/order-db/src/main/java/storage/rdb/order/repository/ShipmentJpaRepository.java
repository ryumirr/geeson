package storage.rdb.order.repository;

import domain.order.entity.ShipmentJpaEntity;
import domain.order.repository.ShipmentRepository;
import module.enums.ShipmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataShipmentJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShipmentJpaRepository implements ShipmentRepository {

    private final SpringDataShipmentJpaRepository repository;

    @Override
    public Optional<ShipmentJpaEntity> findByShipmentId(Long shipmentId) {
        return repository.findById(shipmentId);
    }

    @Override
    public ShipmentJpaEntity save(ShipmentJpaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<ShipmentJpaEntity> findByOrderId(Long orderId) {
        return repository.findByOrderOrderId(orderId);
    }

    @Override
    public void updateStatus(Long shipmentId, ShipmentStatus status) {
        ShipmentJpaEntity shipment = repository.findById(shipmentId)
            .orElseThrow(() -> new IllegalArgumentException("Shipment not found: " + shipmentId));
        shipment.updateStatus(status);
        repository.save(shipment);
    }

    @Override
    public List<ShipmentJpaEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ShipmentJpaEntity> findByTrackingNumber(String trackingNumber) {
        return repository.findByTrackingNumber(trackingNumber);
    }
}
