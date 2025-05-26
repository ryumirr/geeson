package api.order.controller;

import api.order.request.RegisterOrderReq;
import api.order.response.RegisterOrderRes;
import app.order.app.OrderRegisterApp;
import app.order.command.OrderRegisterCommand;
import domain.order.domain.entity.ProductOrderJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderApi {
    private final OrderRegisterApp orderRegisterApp;

    @PostMapping("/")
    public RegisterOrderRes createOrder(
            @RequestBody RegisterOrderReq orderReq
    ) {
        ProductOrderJpaEntity productOrder = orderRegisterApp.registerOrder(new OrderRegisterCommand(
            orderReq.customerId(),
            orderReq.shippingAddressId(),
            orderReq.paymentMethodId(),
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
}
