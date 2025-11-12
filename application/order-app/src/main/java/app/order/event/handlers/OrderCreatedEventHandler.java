package app.order.event.handlers;

import app.order.event.OrderCreatedEvent;
import domain.order.message.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler {

    private final OrderEventPublisher orderEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        orderEventPublisher.publishOrderCreated(event.getPayload());
    }
}
