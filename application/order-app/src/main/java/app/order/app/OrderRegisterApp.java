package app.order.app;

import app.order.app.OrderListApp;
import app.order.command.OrderRegisterCommand;
import app.order.command.OrderRegisterCommand.OrderItem;
import app.order.event.OrderCreatedEvent;
import domain.order.entity.*;
import domain.order.repository.*;
import app.order.exception.CustomerNotFoundException;
import app.order.exception.ShippingAddressNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.messaging.command.OrderStartPayload;
import support.uuid.UuidGenerator;
import grpc.client.InventoryGrpcClient;
import grpc.inventory.ReserveInventoriesResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderRegisterApp {
    private final ProductOrderRepository productOrderRepository;
    private final CustomerRepository customerRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final UuidGenerator uuidGenerator;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final OrderListApp orderListApp;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductOrderJpaEntity registerOrder(OrderRegisterCommand command) {
        CustomerJpaEntity customer = customerRepository.findByCustomerId(command.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        ShippingAddressJpaEntity shippingAddress = shippingAddressRepository
                .findByShippingAddressId(command.shippingAddressId())
                .orElseThrow(() -> new ShippingAddressNotFoundException("shipping address not found"));

        long orderId = uuidGenerator.nextId();

        PaymentRequestJpaEntity paymentRequest = PaymentRequestJpaEntity.builder()
                .paymentId(uuidGenerator.nextId())
                .orderId(orderId)
                .amount(command.getTotalPrice())
                .paymentMethod(command.paymentMethodId().toString())
                .paymentStatus("PENDING")
                .transactionId(command.paymentKey())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProductOrderJpaEntity productOrderEntity = ProductOrderJpaEntity.builder()
                .orderId(orderId)
                .customer(customer)
                .totalPrice(command.getTotalPrice())
                .status("ORDERED")
                .orderDate(LocalDateTime.now())
                .shippingAddress(shippingAddress)
                .payment(paymentRequest)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .orderItems(new ArrayList<>())
                .build();

        List<OrderItemJpaEntity> orderItemEntityList = command.items().stream()
                .map(item -> OrderItemJpaEntity.builder()
                        .orderItemId(uuidGenerator.nextId())
                        .order(productOrderEntity)
                        .productId(item.productId())
                        .quantity(item.quantity())
                        .unitPrice(BigDecimal.valueOf(item.unitPrice()))
                        .totalPrice(command.getTotalPrice())
                        .build())
                .toList();

        productOrderEntity.getOrderItems().addAll(orderItemEntityList); // 양방향 세팅

        // 이제 모두 설정된 상태로 한번에 save
        productOrderRepository.save(productOrderEntity); // cascade 설정되어 있어야 제대로 동작

        applicationEventPublisher.publishEvent(new OrderCreatedEvent(new OrderStartPayload(
                String.valueOf(productOrderEntity.getOrderId()),
                String.valueOf(customer.getCustomerId()),
                String.valueOf(paymentRequest.getPaymentId()),
                paymentRequest.getTransactionId(),
                command.paymentKey(),
                productOrderEntity.getTotalPrice(),
                "KRW",
                orderItemEntityList.stream().map(v -> new OrderStartPayload.OrderItem(
                        String.valueOf(v.getProductId()),
                        v.getQuantity(),
                        v.getUnitPrice())).toList())));
        return productOrderEntity;
    }

    // @todo redisson 처리 추가
    public ReserveInventoriesResponse reserveInventories(List<OrderItem> productQuantities) {
        try {
            // 재고 확인
            Map<Long, Boolean> checked = orderListApp.checkInventories(productQuantities);
            if (checked.values().stream().anyMatch(avail -> !avail)) {
               // log.warn("재고 부족 상품 존재: {}", avail);
                // throw new IllegalArgumentException("재고 부족 상품 존재");
            }
            Map<Long, Integer> newProductQuantities = productQuantities.stream()
                    .collect(Collectors.toMap(
                            OrderItem::productId,
                            OrderItem::quantity,
                            Integer::sum));
            return inventoryGrpcClient.reserveInventories(newProductQuantities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching inventory item", e);
        }
    }
}
