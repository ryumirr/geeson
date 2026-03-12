package domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments_request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestJpaEntity implements Persistable<Long> {

    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private boolean isNew = true;

    @Override
    public Long getId() {
        return this.paymentId;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PostPersist
    public void markNotNew() {
        this.isNew = false;
    }
}
