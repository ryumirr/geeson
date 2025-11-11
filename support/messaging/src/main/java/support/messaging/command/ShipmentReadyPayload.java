package support.messaging.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipmentReadyPayload(
    Long orderId
) {
}
