package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import module.enums.IdempotencyStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotency_log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyLogJpaEntity {

    @Id
    @Column(name = "idempotency_key", length = 128)
    private String idempotencyKey;

    @Column(name = "endpoint", nullable = false)
    private String endpoint;

    @Column(name = "request_hash", columnDefinition = "TEXT")
    private String requestHash;

    @Column(name = "status", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private IdempotencyStatus status;

    @Column(name = "response_data", columnDefinition = "TEXT")
    private String responseData;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static IdempotencyLogJpaEntity create(String idempotencyKey, String endpoint) {
        return IdempotencyLogJpaEntity.builder()
                .idempotencyKey(idempotencyKey)
                .endpoint(endpoint)
                .status(IdempotencyStatus.PENDING)
                .build();
    }

    public void complete(String responseData) {
        this.status = IdempotencyStatus.COMPLETED;
        this.responseData = responseData;
    }

    public void fail(String reason) {
        this.status = IdempotencyStatus.FAILED;
        this.responseData = reason;
    }
}
