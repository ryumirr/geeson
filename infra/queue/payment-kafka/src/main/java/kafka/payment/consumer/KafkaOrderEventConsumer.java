package kafka.payment.consumer;

import app.payment.app.PaymentApp;
import app.payment.command.PaymentConfirmCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.payment.entity.PaymentJpaEntity;
import kafka.payment.PGConfirmRes;
import kafka.payment.TossInfraRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import support.messaging.command.PaymentRequestPayload;
import support.messaging.event.PaymentFailedEvent;
import support.messaging.event.PaymentSucceedEvent;
import support.uuid.UuidGenerator;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventConsumer {
    private final PaymentApp paymentApp;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TossInfraRequest tossInfraRequest;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UuidGenerator uuidGenerator;

    @KafkaListener(topics = "ord-pay-req-cmd", groupId = "payment-consumer-group")
    public void paymentRequestCommand(String command) throws JsonProcessingException {
        log.info("payment request command received: {}", command);

        PaymentRequestPayload payload = objectMapper.readValue(command, PaymentRequestPayload.class);

        try {
            PGConfirmRes res = tossInfraRequest.pgConfirmRequest();

            PaymentJpaEntity paymentJpaEntity = paymentApp.registerPayment(new PaymentConfirmCommand(
                payload.getUserId(),
                payload.getOrderId(),
                "transactionId",
                payload.getAmount(),
                payload.getCurrency(),
                res.status(),
                payload.getPaymentMethodId(),
                new PaymentConfirmCommand.PGConfirmInfo(
                    "TOSS",
                    "test_" + UUID.randomUUID(),
                    "TOSS00001",
                    res.paymentKey(),
                    res.orderId(),
                    res.totalAmount()
                ),
                res.toString()
            ));

            PaymentSucceedEvent paymentSucceed = new PaymentSucceedEvent(
                String.valueOf(uuidGenerator.nextId()),
                payload.getSagaId(),
                payload.getStepId(),
                payload.getOrderId(),
                payload.getPaymentId(),
                "payment succeed"
            );

            kafkaTemplate.send("ord-pay-req-succ-evt", objectMapper.writeValueAsString(paymentSucceed));
        } catch (Exception e) {
            PaymentFailedEvent paymentFailed = new PaymentFailedEvent(
                String.valueOf(uuidGenerator.nextId()),
                payload.getSagaId(),
                payload.getStepId(),
                payload.getOrderId(),
                payload.getPaymentId(),
                e.getMessage()
            );

            kafkaTemplate.send("ord-pay-req-fail-evt", objectMapper.writeValueAsString(paymentFailed));
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "ord-pay-inv-comp-req", groupId = "payment-consumer-group")
    public void paymentInventoryCompensate(String paymentId) throws JsonProcessingException {
        log.info("paymentId compensated: {}", paymentId);

        kafkaTemplate.send("", "");
    }
}
