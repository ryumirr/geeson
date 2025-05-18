<<<<<<<< HEAD:storage/rdb/order-db/src/main/java/storage/rdb/order/core/entity/PaymentJpaEntity.java
package storage.rdb.order.core.entity;
========
package app.order.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/order-app/src/main/java/app/order/domain/entity/PaymentJpaEntity.java

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "payment")
    private ProductOrderJpaEntity order;
}