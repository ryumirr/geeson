package storage.rdb.order.repository.adapter;

import domain.order.domain.entity.ShippingAddressJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataShippingAddressJpaRepository extends JpaRepository<ShippingAddressJpaEntity, Long> {
}