package api.payment.response;

public record PaymentMethodRegisterRes(
    Long customerId,
    String type,
    String provider,
    String maskedNumber,
    String expirationDate,
    String billingKey
) {
}
