package app.payment.command;

import java.math.BigDecimal;

public record PaymentConfirmCommand (
    String customerId,
    String orderId,
    String transactionId,
    BigDecimal amount,
    String currency,
    String status,
    String paymentMethodId,
    PGConfirmInfo pgConfirmInfo,
    String pgRes
) {

    public record PGConfirmInfo (
        String pgName,
        String apiUrl,
        String pgCode,

        String paymentKey,
        String orderId,
        BigDecimal amount
    ) {

    }
}
