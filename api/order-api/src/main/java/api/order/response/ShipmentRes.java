package api.order.response;

import domain.order.entity.ShipmentJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import app.order.port.in.CreateShipmentUseCase.CreateShipmentResult;
import app.order.port.in.GetShipmentUseCase.GetShipmentResult;

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

    public static ShipmentRes from(CreateShipmentResult result) {
        return new ShipmentRes(
                result.shipmentId(),
                result.orderId(),
                result.trackingNumber(),
                result.status(),
                parse(result.shippedDate()),
                parse(result.deliveredDate()),
                parse(result.createdAt()),
                parse(result.updatedAt()));
    }

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

    public static ShipmentRes from(GetShipmentResult result) {
        return new ShipmentRes(
                result.shipmentId(),
                result.orderId(),
                result.trackingNumber(),
                result.status(),
                parse(result.shippedDate()),
                parse(result.deliveredDate()),
                parse(result.createdAt()),
                parse(result.updatedAt()));
    }

    private static LocalDateTime parse(String str) {
        return str != null ? LocalDateTime.parse(str) : null;
    }
    public static ShipmentRes from(Optional<ShipmentJpaEntity> optionalEntity) {
        return optionalEntity
                .map(ShipmentRes::from)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found"));
    }
}
