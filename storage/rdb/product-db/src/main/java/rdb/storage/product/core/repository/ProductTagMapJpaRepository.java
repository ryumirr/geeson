package rdb.storage.product.core.repository;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagMapJpaRepository extends JpaRepository<ProductTagMapJpaRepository, Long> {
}