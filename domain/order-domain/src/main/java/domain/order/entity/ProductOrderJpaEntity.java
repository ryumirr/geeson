package domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProductOrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> orderItems = new ArrayList<>();

    // ✅ 도메인 생성자 (Builder로 대체)
    @Builder(builderMethodName = "createBuilder")
    public static ProductOrderJpaEntity create(
            CustomerJpaEntity customer,
            BigDecimal totalPrice,
            String status,
            LocalDateTime orderDate,
            ShippingAddressJpaEntity shippingAddress,
            List<OrderItemJpaEntity> orderItems
    ) {
        ProductOrderJpaEntity order = new ProductOrderJpaEntity();
        order.customer = customer;
        order.totalPrice = totalPrice;
        order.status = status;
        order.orderDate = orderDate;
        order.shippingAddress = shippingAddress;
        if (orderItems != null) order.orderItems.addAll(orderItems);
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();
        return order;
    }

    public void changeStatus(String newStatus) {
        if (this.status.equals(newStatus)) return;
        if ("SHIPPED".equals(this.status)) {
            throw new IllegalStateException("Shipped orders cannot change status.");
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void addOrderItem(OrderItemJpaEntity item) {
        if(this.orderItems == null) this.orderItems = new ArrayList<>();
        this.orderItems.add(item);
    }
}
