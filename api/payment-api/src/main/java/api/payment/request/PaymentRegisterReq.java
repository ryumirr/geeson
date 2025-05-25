package api.payment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentRegisterReq(
    Long customerId,
    Long orderId,
    BigDecimal amount,
    String currency,
    Long paymentMethodId
) {
}
