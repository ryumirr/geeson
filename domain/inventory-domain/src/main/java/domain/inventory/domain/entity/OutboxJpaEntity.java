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

    public void markAsFailed() {
        this.status = OutboxStatus.FAILED;
    }
}
