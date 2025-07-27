package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import support.constants.payment.PaymentStatus;
import support.uuid.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PaymentJpaEntity {
    @Id
    private Long paymentId;

    private String orderId;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodJpaEntity paymentMethod;

    public PaymentJpaEntity(UuidGenerator uuidGenerator, String orderId, BigDecimal amount, String currency, PaymentStatus status, PaymentMethodJpaEntity paymentMethod) {
        this.paymentId = uuidGenerator.nextId();
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.requestedAt = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
    }
}