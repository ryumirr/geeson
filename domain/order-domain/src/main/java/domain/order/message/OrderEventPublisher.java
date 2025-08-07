package domain.order.message;

import support.messaging.command.OrderStartPayload;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderStartPayload event);
}
