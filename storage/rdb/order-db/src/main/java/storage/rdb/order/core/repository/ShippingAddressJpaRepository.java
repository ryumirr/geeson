package storage.rdb.order.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storage.rdb.order.core.entity.ShippingAddressJpaEntity;

public interface ShippingAddressJpaRepository extends JpaRepository<ShippingAddressJpaEntity, Long> {
}