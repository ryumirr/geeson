package domain.order.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Entity
@Table(name = "dead_letter_queue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeadLetterMessageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String key;
    private String payload;
    private String reason;
    private int retryCount;
    private LocalDateTime failedAt;
    private LocalDateTime nextRetryAt;
    private boolean processed;

    // 명시적 생성자
    private DeadLetterMessageJpaEntity(
            String topic,
            String key,
            String payload,
            String reason,
            int retryCount,
            LocalDateTime failedAt,
            LocalDateTime nextRetryAt,
            boolean processed) {
        this.topic = topic;
        this.key = key;
        this.payload = payload;
        this.reason = reason;
        this.retryCount = retryCount;
        this.failedAt = failedAt;
        this.nextRetryAt = nextRetryAt;
        this.processed = processed;
    }

    public static DeadLetterMessageJpaEntity create(
            String topic,
            String key,
            String payload,
            String reason,
            int retryCount,
            LocalDateTime failedAt,
            LocalDateTime nextRetryAt) {
        return new DeadLetterMessageJpaEntity(
                topic,
                key,
                payload,
                reason,
                retryCount,
                failedAt,
                nextRetryAt,
                false // processed: 기본값
        );
    }

    public void markProcessed() {
        this.processed = true;
    }

    public void increaseRetryCount() {
        this.retryCount++;
    }

    public void updateNextRetryAt(LocalDateTime nextRetryAt) {
        this.nextRetryAt = nextRetryAt;
    }
}