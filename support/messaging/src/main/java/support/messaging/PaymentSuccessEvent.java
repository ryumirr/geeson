package support.messaging;

public record PaymentSuccessEvent (
    Long paymentId,
    Long orderId
) {
}
