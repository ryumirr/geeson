package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import support.uuid.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@NoArgsConstructor
@AllArgsConstructor
public class RefundJpaEntity {
    @Id
    private Long refundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private BigDecimal refundAmount;
    private String refundReason;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
}
