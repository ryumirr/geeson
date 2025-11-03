package app.payment.app;

import app.payment.exception.NoSuchPaymentGatewayException;
import app.payment.exception.TransactionAlreadyCompleteException;
import app.payment.port.TossPaymentRequestPort;
import domain.payment.entity.PaymentGatewayJpaEntity;
import domain.payment.entity.TransactionJpaEntity;
import domain.payment.repository.PaymentGatewayRepository;
import domain.payment.repository.PaymentMethodRepository;
import domain.payment.repository.PaymentRepository;
import domain.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import support.constants.payment.TransactionResultCode;
import support.constants.payment.TransactionType;
import support.constants.payment.VendorCode;
import support.uuid.UuidGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmApp {
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRepository paymentGatewayRepository;
    private final UuidGenerator uuidGenerator;
    private final PaymentMethodRepository paymentMethodRepository;

    private final TossPaymentRequestPort tossPaymentRequestPort;

    // Vendor가 toss일때 결제 승인 성공에 따른 결제 요청
    public TransactionJpaEntity tossPaymentRequest(String paymentKey, String orderId, BigDecimal amount) throws IOException, ParseException {
        PaymentGatewayJpaEntity tossVendor = paymentGatewayRepository.findByVendorCode(VendorCode.TOSS)
            .orElseThrow(() -> new NoSuchPaymentGatewayException("TOSS VENDOR NOT FOUND"));

        Optional<TransactionJpaEntity> pastTransaction = transactionRepository.findByPgOrderId(orderId);

        if(pastTransaction.isPresent() && pastTransaction.get().isCompleted()) {
            throw new TransactionAlreadyCompleteException("TOSS TRANSACTION ALREADY COMPLETED");
        }

        TransactionJpaEntity transaction = transactionRepository.save(new TransactionJpaEntity(
            uuidGenerator.nextId(),
            null,
            tossVendor,
            TransactionType.CONFIRMATION,
            amount,
            orderId,
            paymentKey,
            TransactionResultCode.PENDING,
            "",
            LocalDateTime.now()
        ));

        JSONObject jsonObject = tossPaymentRequestPort.tossPaymentRequest(paymentKey, orderId, amount);

        if(jsonObject.get("failure") == null && jsonObject.get("status").equals("DONE")) {
            transaction.transactionSucceed(jsonObject.toJSONString());
        } else {
            transaction.transactionFailed(jsonObject.toJSONString());
        }

        return transaction;
    }
}
