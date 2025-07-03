package app.payment.app;

import app.payment.command.PaymentConfirmCommand;
import domain.payment.entity.PaymentGatewayJpaEntity;
import domain.payment.entity.PaymentJpaEntity;
import domain.payment.entity.PaymentMethodJpaEntity;
import domain.payment.entity.TransactionJpaEntity;
import app.payment.exception.PaymentMethodNotFoundException;
import domain.payment.repository.PaymentGatewayRepository;
import domain.payment.repository.PaymentMethodRepository;
import domain.payment.repository.PaymentRepository;
import domain.payment.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import support.uuid.UuidGenerator;

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
    private final UuidGenerator uuidGenerator;

    public PaymentJpaEntity registerPayment(PaymentConfirmCommand command) {
        Optional<PaymentJpaEntity> orderPayment = paymentRepository.findByOrderId(Long.valueOf(command.orderId()));

        if(orderPayment.isPresent()) {
            log.info("payment already registered {}", command.orderId());
            return orderPayment.get();
        }

        PaymentMethodJpaEntity paymentMethod = paymentMethodRepository.findByPaymentMethodId(Long.valueOf(command.paymentMethodId()))
            .orElseThrow(() -> new PaymentMethodNotFoundException("payment method not found"));

        PaymentJpaEntity payment = paymentRepository.save(
            PaymentJpaEntity.builder()
                .paymentId(uuidGenerator.nextId())
                .orderId(Long.valueOf(command.orderId()))
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
