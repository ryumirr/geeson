package api.payment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentPaymentMethodRegisterReq(
    Long customerId,
    Long orderId,
    BigDecimal amount,
    String currency,
    Card card
) {
    public record Card (
        String type,
        String paymentMethod,
        String cardNumber,
        String expirationDate
    ) {
    }
}
