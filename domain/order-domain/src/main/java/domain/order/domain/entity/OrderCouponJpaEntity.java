package domain.order.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_coupons")
public class OrderCouponJpaEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_coupon_id")
    private long orderCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private ProductOrderJpaEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponJpaEntity coupon;
}
