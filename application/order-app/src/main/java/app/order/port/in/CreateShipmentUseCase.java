package app.order.port.in;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public interface CreateShipmentUseCase {
    CreateShipmentResult createShipment(CreateShipmentCommand command);

    record CreateShipmentCommand(Long orderId, String trackingNumber) {}
    record CreateShipmentResult(
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
