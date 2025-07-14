package domain.order.entity;

import lombok.Getter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import module.enums.ShipmentStatus;

@Getter
@Entity
@Table(name = "shipments")
public class ShipmentJpaEntity {

    public static ShipmentJpaEntity from(String trackingNumber, ProductOrderJpaEntity order) {
        ShipmentJpaEntity entity = new ShipmentJpaEntity();
        entity.order = order;
        entity.trackingNumber = trackingNumber;
        entity.status = ShipmentStatus.READY.name();
        entity.createdAt = LocalDateTime.now();
        entity.updatedAt = LocalDateTime.now();
        return entity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void updateStatus(ShipmentStatus status) {
        this.status = status.name();
        this.updatedAt = LocalDateTime.now();
    }
}