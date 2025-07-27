package support.messaging.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequestPayload extends CommandPayload {
    private String orderId;
    private String userId;
    private String paymentId;
    private String paymentKey;
    private BigDecimal amount;
    private String paymentMethodId;
    private String currency;
}