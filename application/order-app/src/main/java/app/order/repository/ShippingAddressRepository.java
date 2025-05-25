package app.order.repository;

import domain.order.domain.entity.ShippingAddressJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingAddressRepository {
    Optional<ShippingAddressJpaEntity> findByShippingAddressId(Long shippingAddressId);
}