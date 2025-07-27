package api.payment.controller;

import api.payment.request.PaymentMethodRegisterReq;
import api.payment.request.TossRequest;
import api.payment.response.CustomerPaymentMethodRes;
import api.payment.response.CustomerPaymentRes;
import api.payment.response.PaymentMethodRegisterRes;
import app.payment.app.PaymentRegisterApp;
import app.payment.app.PaymentMethodRegisterApp;
import app.payment.command.PaymentMethodRegisterCommand;
import domain.payment.entity.PaymentJpaEntity;
import domain.payment.entity.PaymentMethodJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentSelectApi {
    private final PaymentMethodRegisterApp paymentMethodRegisterApp;
    private final PaymentRegisterApp paymentRegisterApp;
    private final TossRequest tossRequest;
    
    /**
     * Get all payments for a specific customer
     * @param customerId the ID of the customer
     * @return list of payments for the customer
     */
    @GetMapping("/")
    public List<CustomerPaymentRes> getCustomerPayments(
        @RequestParam Long customerId
    ) {
        List<PaymentJpaEntity> customerPayments = paymentRegisterApp.getCustomerPayments(customerId);
        
        return customerPayments.stream().map(payment -> new CustomerPaymentRes(
            payment.getPaymentId(),
            Long.parseLong(payment.getOrderId()),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getStatus().name(),
            payment.getRequestedAt(),
            payment.getCompletedAt(),
            new CustomerPaymentRes.PaymentMethodInfo(
                payment.getPaymentMethod().getMethodId(),
                payment.getPaymentMethod().getType(),
                payment.getPaymentMethod().getMaskedNumber()
            )
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
            paymentMethod.getProvider().name(),
            paymentMethod.getMaskedNumber(),
            paymentMethod.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")),
            paymentMethod.getBillingKey()
        );
    }
}