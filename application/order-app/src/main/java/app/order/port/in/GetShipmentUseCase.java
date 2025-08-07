package app.order.port.in;
import org.springframework.stereotype.Component;

@Component
public interface GetShipmentUseCase {
    GetShipmentResult getShipmentById(GetShipmentCommand command);

    record GetShipmentCommand(Long shipmentId) {}
    record GetShipmentResult(
        Long shipmentId,
        Long orderId,
        String trackingNumber,
        String status,
        String shippedDate,
        String deliveredDate,
        String createdAt,
        String updatedAt
    ) {}
}
