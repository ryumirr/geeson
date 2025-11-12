package domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import module.enums.ShipmentStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipments", schema = "order_db")
public class ShipmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long shipmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private ProductOrderJpaEntity order;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * ✅ orderId 접근용 getter (편의 메서드)
     * ShipmentRes.from(entity) 에서 entity.getOrderId() 바로 사용 가능
     */
    public Long getOrderId() {
        return (order != null) ? order.getOrderId() : null;
    }

    /**
     * 생성 팩토리 메서드
     */
    public static ShipmentJpaEntity from(String trackingNumber, ProductOrderJpaEntity order) {
        return ShipmentJpaEntity.builder()
                .order(order)
                .trackingNumber(trackingNumber)
                .status(ShipmentStatus.READY.name())
                .shippedDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * 상태 업데이트
     */
    public void updateStatus(ShipmentStatus status) {
        this.status = status.name();
        this.updatedAt = LocalDateTime.now();
    }
}
