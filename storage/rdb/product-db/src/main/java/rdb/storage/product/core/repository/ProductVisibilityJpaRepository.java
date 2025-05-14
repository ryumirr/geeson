package rdb.storage.product.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProductVisibilityJpaRepository extends JpaRepository<ProductVisibilityJpaRepository, Long> {
}
