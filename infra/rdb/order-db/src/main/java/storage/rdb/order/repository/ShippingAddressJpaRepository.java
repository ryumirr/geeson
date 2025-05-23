package storage.rdb.order.repository;

import app.order.domain.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import storage.rdb.order.repository.adapter.SpringDataShippingAddressJpaRepository;

@Repository
@RequiredArgsConstructor
public class ShippingAddressJpaRepository implements ShippingAddressRepository {
    private final SpringDataShippingAddressJpaRepository repository;
}