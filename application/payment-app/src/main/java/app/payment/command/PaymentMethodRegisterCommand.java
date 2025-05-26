package app.payment.command;

import java.time.LocalDate;

public record PaymentMethodRegisterCommand (
    Long customerId,
    String type,
    String cardCode,
    String cardNumber,
    LocalDate expirationDate,

    String provider,
    String billingKey
) {
}
