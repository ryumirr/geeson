package domain.inventory.domain.message;

import support.messaging.command.StockOutCreatedPayload;

public interface StockOutCreatedEventPublisher {
    void publishStockOutCreatedEvent(StockOutCreatedPayload event);
}
