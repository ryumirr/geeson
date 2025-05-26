package storage.rdb.order.repository.adapter;

import domain.order.domain.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, Long> {
}
