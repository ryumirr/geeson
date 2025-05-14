package rdb.storage.order.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.order.core.entity.ShippingAddressJpaEntity;

import java.time.LocalDateTime;

public interface ShippingAddressJpaRepository extends JpaRepository<ShippingAddressJpaEntity, Long> {
}