package storage.rdb.order.repository;

import domain.order.entity.ShippingAddressJpaEntity;
import domain.order.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataShippingAddressJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShippingAddressJpaRepository implements ShippingAddressRepository {
    private final SpringDataShippingAddressJpaRepository repository;

    @Override
    public Optional<ShippingAddressJpaEntity> findByShippingAddressId(Long shippingAddressId) {
        return repository.findById(shippingAddressId);
    }
}