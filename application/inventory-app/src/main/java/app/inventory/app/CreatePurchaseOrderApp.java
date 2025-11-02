package app.inventory.app;

import app.inventory.port.in.CreatePurchaseOrderUseCase;
import domain.inventory.domain.entity.PurchaseOrderJpaEntity;
import domain.inventory.domain.entity.SupplierJpaEntity;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.PurchaseOrderRepository;
import domain.inventory.domain.repository.SupplierRepository;
import domain.inventory.domain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 발주 생성 유즈케이스 구현체 (DDD 스타일)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CreatePurchaseOrderApp implements CreatePurchaseOrderUseCase {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public CreatePurchaseOrderResult handle(CreatePurchaseOrderCommand command) {
        // 1️⃣ 공급자와 창고 조회
        SupplierJpaEntity supplier = supplierRepository.findById(command.supplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found: " + command.supplierId()));

        WarehouseJpaEntity warehouse = warehouseRepository.findById(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + command.warehouseId()));

        // 2️⃣ 팩토리 메서드로 엔티티 생성
        PurchaseOrderJpaEntity entity = PurchaseOrderJpaEntity.create(
                supplier,
                warehouse,
                LocalDateTime.parse(command.orderDate(), formatter),
                command.status(),
                BigDecimal.valueOf(command.totalAmount())
        );

        // 3️⃣ 저장
        PurchaseOrderJpaEntity saved = purchaseOrderRepository.save(entity);

        // 4️⃣ 결과 DTO 반환
        return new CreatePurchaseOrderResult(
                saved.getPurchaseOrderId(),
                saved.getSupplier().getSupplierId(),
                saved.getWarehouse().getWarehouseId(),
                saved.getOrderDate().format(formatter),
                saved.getStatus(),
                saved.getTotalAmount().doubleValue(),
                saved.getOrderDate().format(formatter), // createdAt (지금은 orderDate와 동일)
                saved.getOrderDate().format(formatter)  // updatedAt (지금은 orderDate와 동일)
        );
    }
}
