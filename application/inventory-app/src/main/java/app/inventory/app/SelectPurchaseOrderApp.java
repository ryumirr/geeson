package app.inventory.app;

import app.inventory.port.in.GetPurchaseOrderUseCase;
import app.inventory.port.in.ListPurchaseOrdersUseCase;
import domain.inventory.domain.entity.PurchaseOrderJpaEntity;
import domain.inventory.domain.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 발주 조회 유즈케이스 구현체 (단건 + 복수)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectPurchaseOrderApp implements
        GetPurchaseOrderUseCase,
        ListPurchaseOrdersUseCase {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // ===== 단건 조회 =====
    @Override
    public GetPurchaseOrderResult handle(GetPurchaseOrderQuery query) {
        PurchaseOrderJpaEntity entity = purchaseOrderRepository.findById(query.purchaseOrderId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "PurchaseOrder not found: " + query.purchaseOrderId()));

        return new GetPurchaseOrderResult(
                entity.getPurchaseOrderId(),
                entity.getSupplier().getSupplierId(),
                entity.getWarehouse().getWarehouseId(),
                entity.getOrderDate().format(formatter),
                entity.getStatus(),
                entity.getTotalAmount().doubleValue(),
                entity.getOrderDate().format(formatter),
                entity.getOrderDate().format(formatter)
        );
    }

    // ===== 복수 조회 =====
    @Override
    public ListPurchaseOrdersResult handle(ListPurchaseOrdersQuery query) {
        List<PurchaseOrderJpaEntity> entities =
                purchaseOrderRepository.findAllById(query.purchaseOrderIds());

        List<PurchaseOrderSummary> summaries = entities.stream()
                .map(e -> new PurchaseOrderSummary(
                        e.getPurchaseOrderId(),
                        e.getSupplier().getSupplierId(),
                        e.getWarehouse().getWarehouseId(),
                        e.getOrderDate().format(formatter),
                        e.getStatus(),
                        e.getTotalAmount().doubleValue(),
                        e.getOrderDate().format(formatter),
                        e.getOrderDate().format(formatter)
                ))
                .collect(Collectors.toList());

        return new ListPurchaseOrdersResult(summaries);
    }
}
