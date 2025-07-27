package api.order.controller;

import api.order.request.RegisterOrderReq;
import api.order.response.ProductOrderRes;
import api.order.response.RegisterOrderRes;
import app.order.app.OrderListApp;
import app.order.app.OrderRegisterApp;
import app.order.command.OrderRegisterCommand;
import domain.order.entity.ProductOrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApi {
    private final OrderRegisterApp orderRegisterApp;
    private final OrderListApp orderListApp;

    @PostMapping("")
    public RegisterOrderRes createOrder(
            @RequestBody RegisterOrderReq orderReq
    ) {
        ProductOrderJpaEntity productOrder = orderRegisterApp.registerOrder(new OrderRegisterCommand(
            orderReq.customerId(),
            orderReq.shippingAddressId(),
            orderReq.paymentMethodId(),
            orderReq.paymentKey(),
            orderReq.items().stream().map(v -> new OrderRegisterCommand.OrderItem(
                v.productId(),
                v.productName(),
                v.quantity(),
                v.unitPrice()
            )).toList()
        ));

        return new RegisterOrderRes(
            productOrder.getOrderId(),
            "REQUESTED",
            productOrder.getShippingAddress().formatAddress(),
            new RegisterOrderRes.Customer(
                productOrder.getCustomer().getCustomerId(),
                productOrder.getCustomer().getName()
            ),
            productOrder.getOrderItems().stream().map(v -> new RegisterOrderRes.OrderItem(
                v.getProductId(),
                v.getQuantity(),
                v.getUnitPrice(),
                v.getTotalPrice()
            )).toList(),
            new RegisterOrderRes.Payment(
                productOrder.getPayment().getPaymentId(),
                productOrder.getPayment().getAmount(),
                productOrder.getPayment().getPaymentMethod(),
                productOrder.getPayment().getPaymentStatus(),
                productOrder.getPayment().getTransactionId()
            )
        );
    }

    @GetMapping("")
    public List<ProductOrderRes> test(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size

    ) {
        return orderListApp.getAllOrders(page, size)
            .stream().map( order -> new ProductOrderRes(
                order.getOrderId(),
                order.getStatus(),
                order.getShippingAddress().formatAddress(),
                new ProductOrderRes.Customer(
                    order.getCustomer().getCustomerId(),
                    order.getCustomer().getName()
                ),
                order.getOrderItems().stream().map(item -> new ProductOrderRes.OrderItem(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getTotalPrice()
                )).toList(),
                new ProductOrderRes.Payment(
                    order.getPayment().getPaymentId(),
                    order.getPayment().getAmount(),
                    order.getPayment().getPaymentMethod(),
                    order.getPayment().getPaymentStatus(),
                    order.getPayment().getTransactionId()
                )
            )).toList();
    }
    
    @GetMapping("/")
    public List<ProductOrderRes> getOrdersByCustomerId(
        @RequestParam Long customerId
    ) {
        return orderListApp.getOrdersByCustomerId(customerId)
            .stream().map( order -> new ProductOrderRes(
                order.getOrderId(),
                order.getStatus(),
                order.getShippingAddress().formatAddress(),
                new ProductOrderRes.Customer(
                    order.getCustomer().getCustomerId(),
                    order.getCustomer().getName()
                ),
                order.getOrderItems().stream().map(item -> new ProductOrderRes.OrderItem(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getTotalPrice()
                )).toList(),
                new ProductOrderRes.Payment(
                    order.getPayment().getPaymentId(),
                    order.getPayment().getAmount(),
                    order.getPayment().getPaymentMethod(),
                    order.getPayment().getPaymentStatus(),
                    order.getPayment().getTransactionId()
                )
            )).toList();
    }
}
