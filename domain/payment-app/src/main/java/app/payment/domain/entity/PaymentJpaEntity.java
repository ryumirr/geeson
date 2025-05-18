<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/PaymentJpaEntity.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/PaymentJpaEntity.java

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodJpaEntity paymentMethod;
}