package domain.order.message;

import org.springframework.stereotype.Component;
import support.messaging.command.OrderStartPayload;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderStartPayload event);
}
