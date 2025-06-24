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
        DeadLetterMessageJpaEntity entity = DeadLetterMessageJpaEntity.create(
                topic,
                key,
                payload,
                reason,
                0, // 초기 retryCount
                LocalDateTime.now(), // 실패 시각
                LocalDateTime.now().plusMinutes(10) // 재시도 예정 시각
        );

        deadLetterRepository.save(entity);
    }
}