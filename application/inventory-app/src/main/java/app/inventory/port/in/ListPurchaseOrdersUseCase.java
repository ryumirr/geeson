package app.inventory.port.in;

import java.util.List;

/**
 * 다중 발주 조회 UseCase (입력 포트)
 */
public interface ListPurchaseOrdersUseCase {

    record ListPurchaseOrdersQuery(java.util.List<Long> purchaseOrderIds) {}

    record PurchaseOrderSummary(
            Long purchaseOrderId,
            Long supplierId,
            Long warehouseId,
            String orderDate,
            String status,
            Double totalAmount,
            String createdAt,
            String updatedAt
    ) {}

    record ListPurchaseOrdersResult(List<PurchaseOrderSummary> purchaseOrders) {}

    ListPurchaseOrdersResult handle(ListPurchaseOrdersQuery query);
}
