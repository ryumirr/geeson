package api.payment.controller;

import api.payment.request.PaymentMethodRegisterReq;
import api.payment.response.PaymentMethodRegisterRes;
import app.payment.app.PaymentMethodRegisterApp;
import app.payment.command.PaymentMethodRegisterCommand;
import domain.payment.entity.PaymentMethodJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/paymentmethods")
@RequiredArgsConstructor
@Slf4j
public class PaymentMethodRegisterApi {
    private final PaymentMethodRegisterApp paymentMethodRegisterApp;

    @PostMapping
    public PaymentMethodRegisterRes registerPaymentMethod(
        @RequestBody PaymentMethodRegisterReq paymentMethodRegisterReq
    ) {
        String provider = "test-pg-provider";
        String billingKey = "test-billingKey";

        PaymentMethodJpaEntity paymentMethod = paymentMethodRegisterApp.register(new PaymentMethodRegisterCommand(
            paymentMethodRegisterReq.customerId(),
            paymentMethodRegisterReq.type(),
            paymentMethodRegisterReq.cardCode(),
            paymentMethodRegisterReq.cardNumber(),
            LocalDate.of(
                Integer.parseInt("20" + paymentMethodRegisterReq.expirationDate().substring(3, 5)),
                Integer.parseInt(paymentMethodRegisterReq.expirationDate().substring(0, 2)),
                1
            ),
            provider,
            billingKey
        ));
        return new PaymentMethodRegisterRes(
            paymentMethod.getCustomerId(),
            paymentMethod.getType(),
            paymentMethod.getProvider().name(),
            paymentMethod.getMaskedNumber(),
            paymentMethod.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")),
            paymentMethod.getBillingKey()
        );
    }
}
