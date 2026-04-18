package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import module.enums.OutboxStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_type", nullable = false, length = 64)
    private String eventType;

    @Column(name = "topic", nullable = false, length = 128)
    private String topic;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private OutboxStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Builder.Default
    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    private static final int MAX_RETRIES = 3;

    public static OutboxJpaEntity create(String eventType, String topic, String payload) {
        return OutboxJpaEntity.builder()
                .eventType(eventType)
                .topic(topic)
                .payload(payload)
                .status(OutboxStatus.PENDING)
                .build();
    }

    public void markAsSent() {
        this.status = OutboxStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    // retryCount를 올리고, 최대 재시도 초과 시 FAILED로 마킹 후 true 반환
    public boolean incrementRetry() {
        this.retryCount++;
        if (this.retryCount >= MAX_RETRIES) {
            this.status = OutboxStatus.FAILED;
            return true;
        }
        return false;
    }

    public int getMaxRetries() {
        return MAX_RETRIES;
    }
}
