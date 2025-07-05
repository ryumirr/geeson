package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "product_id")
    private Long productId;

    public static InventoryJpaEntity withId(Long inventoryId) {
        InventoryJpaEntity entity = new InventoryJpaEntity();
        entity.inventoryId = inventoryId;
        return entity;
    }
}