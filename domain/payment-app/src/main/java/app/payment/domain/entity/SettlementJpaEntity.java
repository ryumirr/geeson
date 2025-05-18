<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/SettlementJpaEntity.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/SettlementJpaEntity.java

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