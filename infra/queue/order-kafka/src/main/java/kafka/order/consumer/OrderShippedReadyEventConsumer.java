package kafka.order.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import support.messaging.command.ShipmentReadyPayload;
import app.order.app.OrderUpdateApp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderShippedReadyEventConsumer {

    private final ObjectMapper objectMapper;
    private final OrderUpdateApp OrderUpdateApp;
    // 필요하면 여기서 Inventory 이벤트 Publisher 주입해서 성공/실패 이벤트 발행

    @KafkaListener(
            topics = "ord-ord-ship-succ-event",
            groupId = "order-consumer-group"
    )
    public void handleOrderShipped(String message) {
        log.info("📨 [Order shipped READY :)] Received Kafka message: {}", message);

        try {
            ShipmentReadyPayload payload = objectMapper.readValue(message, ShipmentReadyPayload.class);
            OrderUpdateApp.updateOrderStatus(payload.orderId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("📨 [Order shipped Success :)] Received Kafka message: {}", message);

    }
}
