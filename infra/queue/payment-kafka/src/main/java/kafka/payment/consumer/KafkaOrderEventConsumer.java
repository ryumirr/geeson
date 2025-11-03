package kafka.payment.consumer;

import app.payment.app.PaymentConfirmApp;
import app.payment.app.PaymentMethodRegisterApp;
import app.payment.app.PaymentMethodSelectApp;
import app.payment.app.PaymentRegisterApp;
import app.payment.command.PaymentConfirmCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.payment.entity.PaymentJpaEntity;
import domain.payment.entity.PaymentMethodJpaEntity;
import domain.payment.entity.TransactionJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import support.messaging.command.PaymentRequestPayload;
import support.messaging.event.PaymentFailedEvent;
import support.messaging.event.PaymentSucceedEvent;
import support.uuid.UuidGenerator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventConsumer {
    private final PaymentRegisterApp paymentRegisterApp;
    private final PaymentConfirmApp paymentConfirmApp;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UuidGenerator uuidGenerator;
    private final PaymentMethodSelectApp paymentMethodSelectApp;

    @KafkaListener(topics = "ord-pay-req-cmd", groupId = "payment-consumer-group")
    public void paymentRequestCommand(String command){
        log.info("payment request command received: {}", command);

        PaymentRequestPayload payload = null;

        try {
            payload =objectMapper.readValue(command, PaymentRequestPayload.class);
        } catch (JsonProcessingException e) {
            log.error("failed to parse command: {}", e.getMessage());
            return;
        }

        if(payload.getPaymentKey() == null) {
            log.error("payment key is null");
            return;
        }

        try {
            PaymentMethodJpaEntity paymentMethod = paymentMethodSelectApp.getByPaymentMethodId(Long.valueOf(payload.getPaymentMethodId()));
            TransactionJpaEntity transaction = paymentConfirmApp.tossPaymentRequest(payload.getPaymentKey(), payload.getOrderId(), payload.getAmount());
            PaymentJpaEntity payment = paymentRegisterApp.registerTossPayment(transaction, paymentMethod);

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
            log.error("failed to register payment: {}", e.getMessage());
            PaymentFailedEvent paymentFailed = new PaymentFailedEvent(
                String.valueOf(uuidGenerator.nextId()),
                payload.getSagaId(),
                payload.getStepId(),
                payload.getOrderId(),
                payload.getPaymentId(),
                e.getMessage()
            );

            try {
                kafkaTemplate.send("ord-pay-req-fail-evt", objectMapper.writeValueAsString(paymentFailed));
            } catch (JsonProcessingException ex) {
                log.error("failed to send failed event: {}", ex.getMessage());
            }
        }
    }

    @KafkaListener(topics = "ord-pay-inv-comp-req", groupId = "payment-consumer-group")
    public void paymentInventoryCompensate(String paymentId) throws JsonProcessingException {
        log.info("paymentId compensated: {}", paymentId);

        kafkaTemplate.send("", "");
    }
}
