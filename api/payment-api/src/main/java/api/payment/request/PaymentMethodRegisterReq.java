package api.payment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentMethodRegisterReq(
    Long customerId,

    String type,
    String cardCode,
    String cardNumber,
    String expirationDate
) {
}
