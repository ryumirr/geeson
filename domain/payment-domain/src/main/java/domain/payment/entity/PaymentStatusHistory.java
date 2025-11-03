package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_status_history")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusHistory {
    @Id
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private String previousStatus;
    private String newStatus;
    private String reason;
    private LocalDateTime changedAt;
}
