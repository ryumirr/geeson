<<<<<<<< HEAD:storage/rdb/order-db/src/main/java/storage/rdb/order/core/entity/CustomerJpaEntity.java
package storage.rdb.order.core.entity;
========
package app.order.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/order-app/src/main/java/app/order/domain/entity/CustomerJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
