package support.messaging;

import java.math.BigDecimal;

public record PaymentFailEvent (
    Long paymentId,
    Long orderId,
    String errMsg
) {
}
