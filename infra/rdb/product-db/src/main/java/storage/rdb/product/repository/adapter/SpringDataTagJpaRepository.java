package storage.rdb.product.repository.adapter;

import domain.product.domain.entity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTagJpaRepository extends JpaRepository<TagJpaEntity, Long> {
}
