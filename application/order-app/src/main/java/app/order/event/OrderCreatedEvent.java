package app.order.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import support.messaging.command.OrderStartPayload;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private final OrderStartPayload payload;
}
