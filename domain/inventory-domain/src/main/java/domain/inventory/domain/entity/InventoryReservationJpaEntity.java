package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import module.enums.ReservationStatus;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "inventory_reservations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Long orderId;
    private Integer reservedQuantity;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private String status;

    /**
     * 정적 팩토리 메서드
     * 
     * @param inventoryId
     * @param orderId
     * @param reservedQuantity
     * @param expiresAt
     * @return
     */
    public static InventoryReservationJpaEntity create(
            Long inventoryId,
            Long orderId,
            int reservedQuantity,
            java.sql.Timestamp expiresAt) {
        return InventoryReservationJpaEntity.builder()
                .inventory(InventoryJpaEntity.builder().inventoryId(inventoryId).build())
                .orderId(orderId)
                .reservedQuantity(reservedQuantity)
                .reservedAt(LocalDateTime.now())
                .expiresAt(expiresAt != null ? expiresAt.toLocalDateTime() : null)
                .status(ReservationStatus.RESERVED.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * 상태 변경 메서드
     * 
     * @param status
     */
    public void changeStatus(ReservationStatus status) {
        this.status = status.name();
        this.updatedAt = LocalDateTime.now();
    }
}