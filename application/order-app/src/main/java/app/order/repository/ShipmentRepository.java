package app.order.repository;

import domain.order.domain.entity.ShipmentJpaEntity;

import java.util.Optional;

public interface ShipmentRepository {
    Optional<ShipmentJpaEntity> findByShipmentId(Long shipmentId);
}
