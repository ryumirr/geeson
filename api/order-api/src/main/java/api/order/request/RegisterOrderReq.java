package api.order.request;

import java.util.List;

public record RegisterOrderReq(
        Long customerId,
        Long shippingAddressId,
        Long paymentMethodId,
        String paymentKey,
        List<OrderItem> items
) {
    public record OrderItem(
            Long productId,
            String productName,
            int quantity,
            int unitPrice
    ) {}
}