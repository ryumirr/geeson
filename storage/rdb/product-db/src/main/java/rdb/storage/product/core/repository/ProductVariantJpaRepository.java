package rdb.storage.product.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductVariantJpaRepository extends JpaRepository<ProductVariantJpaRepository, Long> {
}
