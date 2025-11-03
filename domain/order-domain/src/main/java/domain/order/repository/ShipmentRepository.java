package domain.order.repository;

import domain.order.entity.ShipmentJpaEntity;
import module.enums.ShipmentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository {
    Optional<ShipmentJpaEntity> findByShipmentId(Long shipmentId);

    ShipmentJpaEntity save(ShipmentJpaEntity entity);

    List<ShipmentJpaEntity> findByOrderId(Long orderId);

    void updateStatus(Long shipmentId, ShipmentStatus status);

    List<ShipmentJpaEntity> findAll();

    Optional<ShipmentJpaEntity> findByTrackingNumber(String trackingNumber);
}