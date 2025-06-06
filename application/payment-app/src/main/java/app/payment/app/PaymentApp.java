package app.payment.app;

import app.payment.command.PaymentConfirmCommand;
import app.payment.repository.PaymentGatewayRepository;
import domain.payment.domain.entity.PaymentGatewayJpaEntity;
import domain.payment.domain.entity.PaymentJpaEntity;
import domain.payment.domain.entity.PaymentMethodJpaEntity;
import domain.payment.domain.entity.TransactionJpaEntity;
import app.payment.repository.PaymentMethodRepository;
import app.payment.repository.PaymentRepository;
import app.payment.repository.TransactionRepository;
import app.payment.exception.PaymentMethodNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentApp {
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentGatewayRepository paymentGatewayRepository;

    public PaymentJpaEntity registerPayment(PaymentConfirmCommand command) {
        Optional<PaymentJpaEntity> orderPayment = paymentRepository.findByOrderId(command.orderId());

        if(orderPayment.isPresent()) {
            log.info("payment already registered {}", command.orderId());
            return orderPayment.get();
        }

        PaymentMethodJpaEntity paymentMethod = paymentMethodRepository.findByPaymentMethodId(command.paymentMethodId())
            .orElseThrow(() -> new PaymentMethodNotFoundException("payment method not found"));

        PaymentJpaEntity payment = paymentRepository.save(
            PaymentJpaEntity.builder()
                .orderId(command.orderId())
                .amount(command.amount())
                .currency(command.currency())
                .status(command.status())
                .paymentMethod(paymentMethod)
                .requestedAt(LocalDateTime.now())
                .build()
        );

        PaymentGatewayJpaEntity pg = paymentGatewayRepository.save(
            PaymentGatewayJpaEntity.builder()
                .name(command.pgConfirmInfo().pgName())
                .apiUrl(command.pgConfirmInfo().apiUrl())
                .vendorCode(command.pgConfirmInfo().pgCode())
                .build()
        );

        transactionRepository.save(
            TransactionJpaEntity.builder()
                .gateway(pg)
                .pgTransactionId(command.transactionId())
                .payment(payment)
                .transactionType("PAYMENT")
                .amount(payment.getAmount())
                .pgTransactionId(command.pgConfirmInfo().paymentKey())
                .resultCode(payment.getStatus())
                .resultMessage(command.pgRes())
                .build()
        );

        return payment;
    }
}
