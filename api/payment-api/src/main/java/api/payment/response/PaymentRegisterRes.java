package api.payment.response;

import java.math.BigDecimal;

public record PaymentRegisterRes (
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    String currency,
    String status
) {

}
