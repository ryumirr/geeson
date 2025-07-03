package api.payment.controller;

import api.payment.request.PaymentMethodRegisterReq;
import api.payment.request.PaymentRegisterReq;
import api.payment.request.TossRequest;
import api.payment.response.CustomerPaymentMethodRes;
import api.payment.response.PGConfirmRes;
import api.payment.response.PaymentMethodRegisterRes;
import api.payment.response.PaymentRegisterRes;
import app.payment.app.PaymentApp;
import app.payment.app.PaymentMethodRegisterApp;
import app.payment.command.PaymentConfirmCommand;
import app.payment.command.PaymentMethodRegisterCommand;
import domain.payment.entity.PaymentJpaEntity;
import domain.payment.entity.PaymentMethodJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentApi {
    private final PaymentMethodRegisterApp paymentMethodRegisterApp;
    private final PaymentApp paymentApp;
    private final TossRequest tossRequest;

    @GetMapping("/paymentmethod/{customerId}")
    public List<CustomerPaymentMethodRes> getPaymentMethod(
        @PathVariable Long customerId
    ) {
        List<PaymentMethodJpaEntity> customerPaymentMethod = paymentMethodRegisterApp.getCustomerPaymentMethod(customerId);

        return customerPaymentMethod.stream().map(v -> new CustomerPaymentMethodRes(
            v.getCustomerId(),
            v.getMethodId(),
            v.getType(),
            v.getMaskedNumber()
        )).toList();
    }

    @PostMapping("/paymentmethod")
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
            paymentMethod.getProvider(),
            paymentMethod.getMaskedNumber(),
            paymentMethod.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")),
            paymentMethod.getBillingKey()
        );
    }

    @PostMapping("/confirm")
    public PaymentRegisterRes payRequest(
        @RequestBody PaymentRegisterReq paymentRegisterReq
    ) throws IOException {
        Long paymentMethodId = paymentRegisterReq.paymentMethodId();

        PGConfirmRes res = tossRequest.pgConfirmRequest();

        log.info("payment confirm response: {}", res);

        PaymentJpaEntity payment = paymentApp.registerPayment(
            new PaymentConfirmCommand(
                String.valueOf(paymentRegisterReq.customerId()),
                String.valueOf(paymentRegisterReq.orderId()),
                "test_" + UUID.randomUUID(),
                paymentRegisterReq.amount(),
                paymentRegisterReq.currency(),
                res.status(),
                String.valueOf(paymentMethodId),
                new PaymentConfirmCommand.PGConfirmInfo(
                    "TOSS",
                    "test_" + UUID.randomUUID(),
                    "TOSS00001",
                    res.paymentKey(),
                    res.orderId(),
                    res.totalAmount()
                ),
                res.toString()
            )
        );

        return new PaymentRegisterRes(
            payment.getPaymentId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getStatus()
        );
    }
}