package kafka.order.consumer;

import app.order.app.IdempotencyService;
import app.order.app.OrderUpdateApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import support.messaging.command.ShipmentReadyPayload;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderShippedReadyEventConsumer {

    private final ObjectMapper objectMapper;
    private final OrderUpdateApp OrderUpdateApp;
    private final IdempotencyService idempotencyService;

    @KafkaListener(
            topics = "ord-ord-ship-succ-event",
            groupId = "order-consumer-group"
    )
    public void handleOrderShipped(String message) {
        log.info("📨 [Order shipped READY :)] Received Kafka message: {}", message);

        try {
            ShipmentReadyPayload payload = objectMapper.readValue(message, ShipmentReadyPayload.class);
            String idempotencyKey = "ORDER-SHIPPED-" + payload.orderId();

            if (idempotencyService.isAlreadyProcessed(idempotencyKey)) {
                log.info("Duplicate order shipped event, skip. orderId={}", payload.orderId());
                return;
            }

            idempotencyService.markAsProcessing(idempotencyKey, "ord-ord-ship-succ-event");
            OrderUpdateApp.updateOrderStatus(payload.orderId());
            idempotencyService.markAsCompleted(idempotencyKey);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("📨 [Order shipped Success :)] Received Kafka message: {}", message);

    }
}
