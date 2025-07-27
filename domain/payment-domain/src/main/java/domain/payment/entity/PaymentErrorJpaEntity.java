package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_errors")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentErrorJpaEntity {
    @Id
    private Long errorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private String errorCode;
    private String errorMessage;
    private LocalDateTime occurredAt;
}