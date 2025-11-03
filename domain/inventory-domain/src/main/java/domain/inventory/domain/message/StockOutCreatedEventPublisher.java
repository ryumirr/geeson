package domain.inventory.message;

import support.messaging.command.StockOutCreatedPayload;

public interface StockOutCreatedEventPublisher {
    void publishStockOutCreatedEvent(StockOutCreatedPayload event);
}
