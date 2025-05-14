package rdb.storage.inventory.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_reservations")
public class InventoryReservationJapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    private Long orderId;
    private Integer reservedQuantity;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private String status;
    private Long customerId;
    private Long productId;
    private Long warehouseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
