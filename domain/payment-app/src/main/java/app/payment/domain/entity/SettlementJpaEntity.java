package app.payment.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
public class SettlementJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private Long sellerId;
    private BigDecimal settlementAmount;
    private BigDecimal feeAmount;
    private String settlementStatus;
    private LocalDate scheduledDate;
    private LocalDateTime settledAt;
}