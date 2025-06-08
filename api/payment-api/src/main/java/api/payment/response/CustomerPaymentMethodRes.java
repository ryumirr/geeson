package api.payment.response;

public record CustomerPaymentMethodRes (
    Long customerId,
    Long methodId,
    String type,
    String maskedNumber
) {

}
