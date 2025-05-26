package domain.order.domain.message;

import org.springframework.stereotype.Component;
import support.messaging.OrderCreatedEvent;

@Component
public interface OrderEventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
}
