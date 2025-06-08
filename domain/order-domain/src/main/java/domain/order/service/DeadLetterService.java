package domain.order.service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import app.order.repository.DeadLetterRepository;
import domain.order.domain.entity.DeadLetterMessageJpaEntity;

@Service
@RequiredArgsConstructor
public class DeadLetterService {
    private final DeadLetterRepository deadLetterRepository;

    public void saveDeadMessage(String topic, String key, String payload, String reason) {
        DeadLetterMessageJpaEntity entity = new DeadLetterMessageJpaEntity();
        entity.setTopic(topic);
        entity.setKey(key);
        entity.setPayload(payload);
        entity.setReason(reason);
        entity.setRetryCount(0);
        entity.setFailedAt(LocalDateTime.now());
        entity.setNextRetryAt(LocalDateTime.now().plusMinutes(10));
        entity.setProcessed(false);
        deadLetterRepository.save(entity);
    }
}