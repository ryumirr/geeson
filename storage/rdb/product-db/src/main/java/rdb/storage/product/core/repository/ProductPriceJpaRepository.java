package rdb.storage.product.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProductPriceJpaRepository extends JpaRepository<ProductPriceJpaRepository, Long> {
}
