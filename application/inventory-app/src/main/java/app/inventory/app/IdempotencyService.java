package app.inventory.app;

import domain.inventory.domain.entity.IdempotencyLogJpaEntity;
import domain.inventory.domain.repository.IdempotencyLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import module.enums.IdempotencyStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotencyService {

    private final IdempotencyLogRepository idempotencyLogRepository;

    public boolean isAlreadyProcessed(String idempotencyKey) {
        return idempotencyLogRepository.findById(idempotencyKey)
                .map(log -> log.getStatus() == IdempotencyStatus.COMPLETED)
                .orElse(false);
    }

    @Transactional
    public void markAsProcessing(String idempotencyKey, String endpoint) {
        idempotencyLogRepository.save(IdempotencyLogJpaEntity.create(idempotencyKey, endpoint));
    }

    @Transactional
    public void markAsCompleted(String idempotencyKey) {
        idempotencyLogRepository.findById(idempotencyKey).ifPresent(log -> {
            log.complete("SUCCESS");
            idempotencyLogRepository.save(log);
        });
    }

    @Transactional
    public void markAsFailed(String idempotencyKey, String reason) {
        idempotencyLogRepository.findById(idempotencyKey).ifPresent(log -> {
            log.fail(reason);
            idempotencyLogRepository.save(log);
        });
    }
}
