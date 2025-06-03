package api.order.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductOrderRes(
    Long orderId,
    String orderStatus,
    String shippingAddress,
    Customer customer,
    List<OrderItem> item,
    Payment payment

) {
    public record Customer(
        Long customerId,
        String customerName
    ) {

    }

    public record OrderItem(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
    ) {
    }

    public record Payment(
        Long paymentId,
        BigDecimal amount,
        String paymentMethod,
        String paymentStatus,
        String transactionId
    ) {
    }
}
