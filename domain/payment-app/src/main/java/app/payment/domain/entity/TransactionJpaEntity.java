<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/TransactionJpaEntity.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/TransactionJpaEntity.java

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id")
    private PaymentGatewayJpaEntity gateway;

    private String transactionType;
    private BigDecimal amount;
    private String pgTransactionId;
    private String resultCode;
    private String resultMessage;
    private LocalDateTime transactionTime;
}