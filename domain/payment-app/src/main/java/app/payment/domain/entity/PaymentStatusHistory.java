<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/PaymentStatusHistory.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/PaymentStatusHistory.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_status_history")
public class PaymentStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private String previousStatus;
    private String newStatus;
    private String reason;
    private LocalDateTime changedAt;
}
