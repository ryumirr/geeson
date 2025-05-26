package kafka.payment.consumer;

import app.payment.app.PaymentApp;
import app.payment.command.PaymentConfirmCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.payment.PGConfirmRes;
import kafka.payment.TossInfraRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import support.messaging.OrderCreatedEvent;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderEventConsumer {
    private final PaymentApp paymentApp;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TossInfraRequest tossInfraRequest;

    @KafkaListener(topics = "order.created", groupId = "order-consumer-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(record.value(), OrderCreatedEvent.class);

            PGConfirmRes res = tossInfraRequest.pgConfirmRequest();

            paymentApp.registerPayment(new PaymentConfirmCommand(
                event.customerId(),
                event.orderId(),
                event.transactionId(),
                event.totalPrice(),
                event.currency(),
                res.status(),
                event.paymentMethodId(),
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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
