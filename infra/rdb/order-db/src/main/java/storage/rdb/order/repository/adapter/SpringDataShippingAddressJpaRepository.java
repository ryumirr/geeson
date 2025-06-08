package storage.rdb.order.repository.adapter;

import domain.order.entity.ShippingAddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataShippingAddressJpaRepository extends JpaRepository<ShippingAddressJpaEntity, Long> {
}