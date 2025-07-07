package support.messaging.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InventoryReserveSucceedEvent(
    String eventId,
    String sagaId,
    String stepId,
    String orderId,
    String productId,
    String inventoryId,
    String message
) {
}