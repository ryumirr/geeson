package api.payment.controller;

import api.payment.response.CustomerPaymentMethodRes;
import app.payment.app.PaymentMethodRegisterApp;
import app.payment.app.PaymentMethodSelectApp;
import domain.payment.entity.PaymentMethodJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paymentmethods")
@RequiredArgsConstructor
@Slf4j
public class PaymentMethodSelectApi {
    private final PaymentMethodSelectApp paymentMethodSelectApp;

    @GetMapping("/{customerId}")
    public List<CustomerPaymentMethodRes> getPaymentMethod(
        @PathVariable Long customerId
    ) {
        List<PaymentMethodJpaEntity> customerPaymentMethod = paymentMethodSelectApp.getCustomerPaymentMethod(customerId);

        return customerPaymentMethod.stream().map(v -> new CustomerPaymentMethodRes(
            v.getCustomerId(),
            v.getMethodId(),
            v.getType(),
            v.getMaskedNumber()
        )).toList();
    }
}
