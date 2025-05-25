package app.order.command;

import java.math.BigDecimal;
import java.util.List;

public record OrderRegisterCommand(
        Long customerId,
        Long shippingAddressId,
        Long paymentMethodId,
        List<OrderItem> items
) {
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderItem item : items) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(item.unitPrice()).multiply(BigDecimal.valueOf(item.quantity())));
        }
        return totalPrice;
    }

    public record OrderItem(
            Long productId,
            String productName,
            int quantity,
            int unitPrice
    ) {}
}