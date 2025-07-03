package support.messaging.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentSucceedEvent(
    String eventId,
    String sagaId,
    String stepId,
    String orderId,
    String paymentId,
    String message
) {
}