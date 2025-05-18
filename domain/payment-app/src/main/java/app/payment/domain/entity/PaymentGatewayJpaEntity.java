<<<<<<<< HEAD:storage/rdb/payment-db/src/main/java/storage/rdb/payment/core/entity/PaymentGatewayJpaEntity.java
package storage.rdb.payment.core.entity;
========
package app.payment.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/payment-app/src/main/java/app/payment/domain/entity/PaymentGatewayJpaEntity.java

import jakarta.persistence.*;

@Entity
@Table(name = "payment_gateways")
public class PaymentGatewayJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gatewayId;
    private String name;
    private String apiUrl;
    private String vendorCode;
    private Boolean active;
}