package domain.inventory.domain.message;

import support.messaging.command.StockOutCreatedPayload;
import support.messaging.command.ShipmentReadyPayload;

public interface InventoryEventPublisher {
    void publishStockOutCreatedEvent(StockOutCreatedPayload event);
    void publishShipmentReady(ShipmentReadyPayload payload);
}
