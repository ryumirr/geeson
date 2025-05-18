<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/PaymentMethodJpaEntity.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/PaymentMethodJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
public class PaymentMethodJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodId;

    private Long customerId;
    private String type; // CARD, ACCOUNT, MOBILE, etc
    private String provider;
    private String maskedNumber;
    private LocalDate expirationDate;
    private String billingKey;
    private LocalDateTime createdAt;
}

