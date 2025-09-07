package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "inventory_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemsJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_item_id")
    private Long inventoryItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryJpaEntity inventory;

    @Column(name = "batch_lot_id")
    private Long batchLotId;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column(name = "status")
    private String status; // Enum 추천: AVAILABLE, RESERVED, DAMAGED 등

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null)
            this.status = "AVAILABLE";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static InventoryItemsJpaEntity from(
            InventoryJpaEntity inventory,
            Long batchLotId,
            String serialNumber,
            String status) {
        return InventoryItemsJpaEntity.builder()
                .inventory(inventory)
                .batchLotId(batchLotId)
                .serialNumber(serialNumber)
                .status(Optional.ofNullable(status).orElse("AVAILABLE"))
                .build();
    }

}
