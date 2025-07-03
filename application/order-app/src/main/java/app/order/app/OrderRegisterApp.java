package app.order.app;

import app.order.command.OrderRegisterCommand;
import domain.order.entity.*;
import domain.order.message.OrderEventPublisher;
import app.order.exception.CustomerNotFoundException;
import app.order.exception.ShippingAddressNotFoundException;
import domain.order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.messaging.command.OrderStartPayload;
import support.uuid.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderRegisterApp {
    private final ProductOrderRepository productOrderRepository;
    private final CustomerRepository customerRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final UuidGenerator uuidGenerator;

    private final OrderEventPublisher orderEventPublisher;

    public ProductOrderJpaEntity registerOrder(OrderRegisterCommand command) {
        CustomerJpaEntity customer = customerRepository.findByCustomerId(command.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        ShippingAddressJpaEntity shippingAddress = shippingAddressRepository.findByShippingAddressId(command.shippingAddressId())
                .orElseThrow(() -> new ShippingAddressNotFoundException("shipping address not found"));

        ProductOrderJpaEntity productOrderEntity = ProductOrderJpaEntity.builder()
            .orderId(uuidGenerator.nextId())
            .customer(customer)
            .totalPrice(command.getTotalPrice())
            .status("ORDERED")
            .orderDate(LocalDateTime.now())
            .shippingAddress(shippingAddress)
            .payment(null)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .orderItems(new ArrayList<>())
            .build();

        List<OrderItemJpaEntity> orderItemEntityList = command.items().stream()
            .map(item -> OrderItemJpaEntity.builder()
                .order(productOrderEntity)
                .productId(item.productId())
                .quantity(item.quantity())
                .unitPrice(BigDecimal.valueOf(item.unitPrice()))
                .totalPrice(command.getTotalPrice())
                .build()
            ).toList();

        PaymentRequestJpaEntity paymentRequestEntity = PaymentRequestJpaEntity.builder()
            .paymentId(uuidGenerator.nextId())
            .orderId(productOrderEntity.getOrderId())
            .order(productOrderEntity)
            .amount(productOrderEntity.getTotalPrice())
            .paymentMethod(String.valueOf(command.paymentMethodId()))
            .transactionId(UUID.randomUUID().toString())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        productOrderEntity.getOrderItems().addAll(orderItemEntityList); // 양방향 세팅

        productOrderEntity.registerPayment(paymentRequestEntity); // 등록

        // 이제 모두 설정된 상태로 한번에 save
        productOrderRepository.save(productOrderEntity); // cascade 설정되어 있어야 제대로 동작

        orderEventPublisher.publishOrderCreated(new OrderStartPayload(
            String.valueOf(productOrderEntity.getOrderId()),
            String.valueOf(customer.getCustomerId()),
            paymentRequestEntity.getPaymentMethod(),
            paymentRequestEntity.getTransactionId(),
            productOrderEntity.getTotalPrice(),
            "KRW",
            orderItemEntityList.stream().map(v -> new OrderStartPayload.OrderItem(
                String.valueOf(v.getProductId()),
                v.getQuantity(),
                v.getUnitPrice()
            )).toList()
        ));

        return productOrderEntity;
    }
}
