package app.order.command;

public record CreateShipmentCommand(Long orderId, String trackingNumber) {
}
