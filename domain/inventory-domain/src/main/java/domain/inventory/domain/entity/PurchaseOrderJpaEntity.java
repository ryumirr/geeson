package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA 프록시용 기본 생성자 (외부에서 new 금지)
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // Builder 전용
@Builder
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierJpaEntity supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WarehouseJpaEntity warehouse;

    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;

    // ===== 팩토리 메서드 =====
    public static PurchaseOrderJpaEntity create(
            SupplierJpaEntity supplier,
            WarehouseJpaEntity warehouse,
            LocalDateTime orderDate,
            String status,
            BigDecimal totalAmount
    ) {
        return PurchaseOrderJpaEntity.builder()
                .supplier(supplier)
                .warehouse(warehouse)
                .orderDate(orderDate)
                .status(status)
                .totalAmount(totalAmount)
                .build();
    }

    // ===== 비즈니스 로직 =====
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void updateTotalAmount(BigDecimal newTotal) {
        this.totalAmount = newTotal;
    }

    // ===== 도메인 규칙 =====
    public boolean isCompleted() {
        return "COMPLETED".equalsIgnoreCase(this.status);
    }

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(this.status);
    }
}
