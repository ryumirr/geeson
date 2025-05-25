package support.messaging;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEvent (
    Long orderId,
    Long customerId,
    Long paymentMethodId,
    String transactionId,
    BigDecimal totalPrice,
    String currency,
    List<OrderItem> items
) {
    public record OrderItem(
        String productId,
        int quantity,
        int unitPrice
    ) {}
}
