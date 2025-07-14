package api.order.response;

import domain.order.entity.ShipmentJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentRes {

    private Long shipmentId;
    private Long orderId;
    private String trackingNumber;
    private String status;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ShipmentRes from(ShipmentJpaEntity entity) {
        return new ShipmentRes(
                entity.getShipmentId(),
                entity.getOrder().getOrderId(),
                entity.getTrackingNumber(),
                entity.getStatus(),
                entity.getShippedDate(),
                entity.getDeliveredDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static ShipmentRes from(Optional<ShipmentJpaEntity> optionalEntity) {
        return optionalEntity
                .map(ShipmentRes::from)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));
    }
}