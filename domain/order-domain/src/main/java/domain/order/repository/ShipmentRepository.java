package domain.order.repository;

import domain.order.entity.ShipmentJpaEntity;

import java.util.Optional;

public interface ShipmentRepository {
    Optional<ShipmentJpaEntity> findByShipmentId(Long shipmentId);
}
