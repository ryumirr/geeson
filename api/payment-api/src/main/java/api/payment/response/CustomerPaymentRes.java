package api.payment.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for customer payment information
 */
public record CustomerPaymentRes(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    String currency,
    String status,
    LocalDateTime requestedAt,
    LocalDateTime completedAt,
    PaymentMethodInfo paymentMethod
) {
    /**
     * Payment method information
     */
    public record PaymentMethodInfo(
        Long methodId,
        String type,
        String maskedNumber
    ) {}
}