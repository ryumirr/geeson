package app.order.app;

import app.order.command.OrderRegisterCommand;
import app.order.repository.*;
import domain.order.domain.entity.*;
import domain.order.domain.message.OrderEventPublisher;
import app.order.exception.CustomerNotFoundException;
import app.order.exception.ShippingAddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.messaging.OrderCreatedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderRegisterApp {
    private final ProductOrderRepository productOrderRepository;
    private final CustomerRepository customerRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRequestRepository paymentRequestRepository;

    private final OrderEventPublisher orderEventPublisher;

    public ProductOrderJpaEntity registerOrder(OrderRegisterCommand command) {
        CustomerJpaEntity customer = customerRepository.findByCustomerId(command.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        ShippingAddressJpaEntity shippingAddress = shippingAddressRepository.findByShippingAddressId(command.shippingAddressId())
                .orElseThrow(() -> new ShippingAddressNotFoundException("shipping address not found"));

        ProductOrderJpaEntity ordered = productOrderRepository.save(ProductOrderJpaEntity.builder()
            .customer(customer)
            .totalPrice(command.getTotalPrice())
            .status("ORDERED")
            .orderDate(LocalDateTime.now())
            .shippingAddress(shippingAddress)
            .payment(null)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .orderItems(null)
            .build()
        );

        List<OrderItemJpaEntity> orderItems = orderItemRepository.saveAll(
            command.items().stream()
                .map(item -> OrderItemJpaEntity.builder()
                    .order(ordered)
                    .productId(item.productId())
                    .quantity(item.quantity())
                    .unitPrice(BigDecimal.valueOf(item.unitPrice()))
                    .totalPrice(command.getTotalPrice())
                    .build()
                ).toList()
        );

        PaymentRequestJpaEntity paymentRequest = paymentRequestRepository.save(
            PaymentRequestJpaEntity.builder()
                .orderId(ordered.getOrderId())
                .order(ordered)
                .amount(ordered.getTotalPrice())
                .paymentMethod(String.valueOf(command.paymentMethodId()))
                .transactionId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
        );

        orderItems.forEach(v -> v.registerOrder(ordered));
        ordered.registerPayment(paymentRequest);

        orderEventPublisher.publishOrderCreated(new OrderCreatedEvent(
            ordered.getOrderId(),
            customer.getCustomerId(),
            Long.valueOf(paymentRequest.getPaymentMethod()),
            paymentRequest.getTransactionId(),
            ordered.getTotalPrice(),
            "KRW",
            orderItems.stream().map(v -> new OrderCreatedEvent.OrderItem(
                String.valueOf(v.getProductId()),
                v.getQuantity(),
                v.getUnitPrice().intValue()
            )).toList()
        ));

        return ordered;
    }
}
