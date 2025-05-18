<<<<<<<< HEAD:storage/rdb/order-db/src/main/java/storage/rdb/order/core/entity/OrderStatusHistoryJpaEntity.java
package storage.rdb.order.core.entity;
========
package app.order.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/order-app/src/main/java/app/order/domain/entity/OrderStatusHistoryJpaEntity.java

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private ProductOrderJpaEntity order;

    private String status;
    private LocalDateTime changedAt;
}
