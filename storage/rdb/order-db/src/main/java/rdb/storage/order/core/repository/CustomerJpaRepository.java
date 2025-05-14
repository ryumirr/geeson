package rdb.storage.order.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rdb.storage.order.core.entity.CustomerJpaEntity;

import java.time.LocalDateTime;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
