package storage.rdb.inventory.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import domain.inventory.domain.entity.InventoryItemsJpaEntity;
import domain.inventory.domain.repository.InventoryItemsRepository;
import lombok.RequiredArgsConstructor;
import storage.rdb.inventory.repository.adapter.SpringDataInventoryItemsJpaRepository;

@Repository
@RequiredArgsConstructor
public class InventoryItemsJpaRepository  implements InventoryItemsRepository {
     private final SpringDataInventoryItemsJpaRepository repository;

     @Override
     public InventoryItemsJpaEntity save(InventoryItemsJpaEntity entity) {
          return repository.save(entity);
     }

     @Override
     public Optional<InventoryItemsJpaEntity> findById(Long id) {
          return repository.findById(id);
     }
}
