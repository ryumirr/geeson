package api.order.controller;

import api.order.request.RegisterOrderReq;
import api.order.response.ProductOrderRes;
import api.order.response.RegisterOrderRes;
import app.order.app.OrderListApp;
import app.order.app.OrderRegisterApp;
import app.order.app.OrderRegisterApp.TestInventoryItemRes;
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

        // @todo [2025-09-17] DELETE this endpoint after confirming gRPC inventory fetch
        @PostMapping("/testCreateOrder")
        public TestOrderRes testCreateOrder(@RequestBody RegisterOrderReq orderReq) {
                List<RegisterOrderReq.OrderItem> items = orderReq.items();
                long productId = items.get(0).productId();

                var item = orderRegisterApp.createInventoryItem(
                                orderReq.customerId(),
                                "SERIAL-" + productId,
                                "READY");

                return new TestOrderRes(orderReq, item);
        }

        // @todo [2025-09-17] DELETE this endpoint after confirming gRPC inventory fetch
        @GetMapping("/testGetOrder")
        public TestOrderRes testGetOrder(
                        @RequestBody RegisterOrderReq orderReq) {
                var item = orderRegisterApp.testSelectInventoryItem(1L);

                // Order 등록 결과를 InventoryItem과 함께 응답
                return new TestOrderRes(orderReq, item);
        }

        // @todo [2025-09-17] DELETE this endpoint after confirming gRPC inventory fetch
        @GetMapping("/testGetInvenItemsBySerialNum")
        public TestOrderRes testGetInvenItemsBySerialNum(
                        @RequestParam("serialNum") String serialNum) {
                var item = orderRegisterApp.selectInventoryItemBySerialNumber(serialNum);
                return new TestOrderRes(serialNum, item);
        }

        @PostMapping("")
        public RegisterOrderRes createOrder(
                        @RequestBody RegisterOrderReq orderReq) {

                // 재고 확인
                List<RegisterOrderReq.OrderItem> items = orderReq.items();
                long productId = items.get(0).productId();
                orderRegisterApp.selectInventoryItemBySerialNumber("SERIAL-" + productId);

                ProductOrderJpaEntity productOrder = orderRegisterApp.registerOrder(new OrderRegisterCommand(
                                orderReq.customerId(),
                                orderReq.shippingAddressId(),
                                orderReq.paymentMethodId(),
                                orderReq.paymentKey(),
                                orderReq.items().stream().map(v -> new OrderRegisterCommand.OrderItem(
                                                v.productId(),
                                                v.productName(),
                                                v.quantity(),
                                                v.unitPrice())).toList()));

                return new RegisterOrderRes(
                                productOrder.getOrderId(),
                                "REQUESTED",
                                productOrder.getShippingAddress().formatAddress(),
                                new RegisterOrderRes.Customer(
                                                productOrder.getCustomer().getCustomerId(),
                                                productOrder.getCustomer().getName()),
                                productOrder.getOrderItems().stream().map(v -> new RegisterOrderRes.OrderItem(
                                                v.getProductId(),
                                                v.getQuantity(),
                                                v.getUnitPrice(),
                                                v.getTotalPrice())).toList(),
                                new RegisterOrderRes.Payment(
                                                productOrder.getPayment().getPaymentId(),
                                                productOrder.getPayment().getAmount(),
                                                productOrder.getPayment().getPaymentMethod(),
                                                productOrder.getPayment().getPaymentStatus(),
                                                productOrder.getPayment().getTransactionId()));
        }

        @GetMapping("")
        public List<ProductOrderRes> test(
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "10") int size

        ) {
                return orderListApp.getAllOrders(page, size)
                                .stream().map(order -> new ProductOrderRes(
                                                order.getOrderId(),
                                                order.getStatus(),
                                                order.getShippingAddress().formatAddress(),
                                                new ProductOrderRes.Customer(
                                                                order.getCustomer().getCustomerId(),
                                                                order.getCustomer().getName()),
                                                order.getOrderItems().stream()
                                                                .map(item -> new ProductOrderRes.OrderItem(
                                                                                item.getProductId(),
                                                                                item.getQuantity(),
                                                                                item.getUnitPrice(),
                                                                                item.getTotalPrice()))
                                                                .toList(),
                                                new ProductOrderRes.Payment(
                                                                order.getPayment().getPaymentId(),
                                                                order.getPayment().getAmount(),
                                                                order.getPayment().getPaymentMethod(),
                                                                order.getPayment().getPaymentStatus(),
                                                                order.getPayment().getTransactionId())))
                                .toList();
        }

        @GetMapping("/")
        public List<ProductOrderRes> getOrdersByCustomerId(
                        @RequestParam Long customerId) {
                return orderListApp.getOrdersByCustomerId(customerId)
                                .stream().map(order -> new ProductOrderRes(
                                                order.getOrderId(),
                                                order.getStatus(),
                                                order.getShippingAddress().formatAddress(),
                                                new ProductOrderRes.Customer(
                                                                order.getCustomer().getCustomerId(),
                                                                order.getCustomer().getName()),
                                                order.getOrderItems().stream()
                                                                .map(item -> new ProductOrderRes.OrderItem(
                                                                                item.getProductId(),
                                                                                item.getQuantity(),
                                                                                item.getUnitPrice(),
                                                                                item.getTotalPrice()))
                                                                .toList(),
                                                new ProductOrderRes.Payment(
                                                                order.getPayment().getPaymentId(),
                                                                order.getPayment().getAmount(),
                                                                order.getPayment().getPaymentMethod(),
                                                                order.getPayment().getPaymentStatus(),
                                                                order.getPayment().getTransactionId())))
                                .toList();
        }
}

// @todo [2025-09-17] DELETE this endpoint after confirming gRPC inventory fetch
// is stable
// Test response class for Order
class TestOrderRes {
        private Long customerId;
        private Object item;
        private String serialNum;

        public TestOrderRes(RegisterOrderReq req, Object item) {
                this.customerId = req.customerId();
                this.item = item;
        }

        public TestOrderRes(String serialNum, TestInventoryItemRes item) {
                this.serialNum = serialNum;
                this.item = item;
        }

        public Object getItem() {
                return item;
        }

}
