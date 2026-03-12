package kafka.inventory.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.inventory.domain.message.InventoryEventPublisher;
import domain.inventory.domain.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import support.messaging.command.ShipmentReadyPayload;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPollingScheduler {

    private final OutboxRepository outboxRepository;
    private final InventoryEventPublisher inventoryEventPublisher;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void pollAndPublish() {
        var events = outboxRepository.findPendingEvents();
        if (events.isEmpty()) return;

        log.info("📬 Outbox polling: {} pending event(s) found", events.size());

        for (var event : events) {
            try {
                if ("SHIPMENT_READY".equals(event.getEventType())) {
                    ShipmentReadyPayload payload = objectMapper.readValue(event.getPayload(), ShipmentReadyPayload.class);
                    inventoryEventPublisher.publishShipmentReady(payload);
                }

                event.markAsSent();
                outboxRepository.save(event);
                log.info("✅ Outbox event published. id={}, eventType={}", event.getId(), event.getEventType());

            } catch (Exception e) {
                log.error("❌ Failed to publish outbox event. id={}", event.getId(), e);
                event.markAsFailed();
                outboxRepository.save(event);
            }
        }
    }
}
