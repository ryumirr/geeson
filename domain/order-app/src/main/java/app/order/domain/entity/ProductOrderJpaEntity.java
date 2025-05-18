<<<<<<<< HEAD:storage/rdb/order-db/src/main/java/storage/rdb/order/core/entity/ProductOrderJpaEntity.java
package storage.rdb.order.core.entity;
========
package app.order.domain.entity;
>>>>>>>> 075f59a46cc79b02892a5dc70f9be8f1ed6351d8:domain/order-app/src/main/java/app/order/domain/entity/ProductOrderJpaEntity.java

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class ProductOrderJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;

    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime orderDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddressJpaEntity shippingAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemJpaEntity> orderItems;
}
