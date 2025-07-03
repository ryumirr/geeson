package domain.order.message;

import org.springframework.stereotype.Component;
import support.messaging.command.OrderStartPayload;

@Component
public interface OrderEventPublisher {
    void publishOrderCreated(OrderStartPayload event);
}
