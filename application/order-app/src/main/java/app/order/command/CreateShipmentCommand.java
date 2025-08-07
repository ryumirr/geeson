package app.order.command;

public record CreateShipmentCommand(Long orderId, String carrier, String trackingNumber) {
}
