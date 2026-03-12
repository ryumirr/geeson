package domain.order.message;

import support.messaging.command.OrderStartPayload;
import support.messaging.command.ShipmentReadyPayload;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderStartPayload event);
    void publishOrderShipped(ShipmentReadyPayload event);
}
