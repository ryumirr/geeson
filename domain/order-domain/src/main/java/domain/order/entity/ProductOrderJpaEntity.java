package domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
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
public class ProductOrderJpaEntity implements Persistable<Long> {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Version
    private long version; // 낙관적 에러 방지용

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

    // Persistable 구현 부분(Snowflake id사용 위하여 임시추가)
    @Transient
    private boolean isNew = true;

    @Override
    public Long getId() {
        return this.orderId;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @PostPersist
    public void markNotNew() {
        this.isNew = false;
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
        if (this.orderItems == null)
            this.orderItems = new ArrayList<>();
        this.orderItems.add(item);
    }
}
