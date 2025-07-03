package support.messaging.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InventoryReserveFailedEvent (
    String eventId,
    String sagaId,
    String stepId,
    String orderId,
    String inventoryId,
    String reason
) {
}