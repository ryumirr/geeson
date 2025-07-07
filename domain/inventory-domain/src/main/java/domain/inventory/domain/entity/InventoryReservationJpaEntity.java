package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import module.enums.ReservationStatus;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

@Entity
@Table(name = "inventory_reservations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservationJpaEntity {

    @Id
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "status", columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    /**
     * 정적 팩토리 메서드
     * 
     * @param inventory
     * @param orderId
     * @param reservedQuantity
     * @param expiresAt
     * @return
     */
    public static InventoryReservationJpaEntity create(
        Long reservationId,
        InventoryJpaEntity inventory,
        Long orderId,
        int reservedQuantity,
        LocalDateTime expiresAt
    ) {
        return InventoryReservationJpaEntity.builder()
            .reservationId(reservationId)
            .inventory(inventory)
            .orderId(orderId)
            .reservedQuantity(reservedQuantity)
            .reservedAt(LocalDateTime.now())
            .expiresAt(expiresAt)
            .status(ReservationStatus.RESERVED)
            .build();
    }

    /**
     * 상태 변경 메서드
     * 
     * @param status
     */
    public void changeStatus(ReservationStatus status) {
        this.status = status;
    }
}