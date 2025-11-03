package storage.rdb.order.repository.adapter;

import domain.order.entity.ShipmentJpaEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, Long> {

    List<ShipmentJpaEntity> findByOrderOrderId(Long orderId);

    Optional<ShipmentJpaEntity> findByTrackingNumber(String trackingNumber);
}
