package domain.order.repository;

import domain.order.entity.ShippingAddressJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingAddressRepository {
    Optional<ShippingAddressJpaEntity> findByShippingAddressId(Long shippingAddressId);
}