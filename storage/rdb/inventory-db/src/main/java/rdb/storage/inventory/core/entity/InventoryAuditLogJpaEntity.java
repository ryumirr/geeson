package rdb.storage.inventory.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_audit_logs")
public class InventoryAuditLogJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private InventoryJpaEntity inventory;

    private String action;
    private Integer quantity;
    private Integer previousQuantity;
    private Integer currentQuantity;
    private String performedBy;
    private LocalDateTime performedAt;
}
