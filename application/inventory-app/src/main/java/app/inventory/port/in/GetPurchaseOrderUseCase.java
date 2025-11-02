package app.inventory.port.in;

/**
 * 단일 발주 조회 UseCase (입력 포트)
 */
public interface GetPurchaseOrderUseCase {

    record GetPurchaseOrderQuery(Long purchaseOrderId) {}

    record GetPurchaseOrderResult(
            Long purchaseOrderId,
            Long supplierId,
            Long warehouseId,
            String orderDate,
            String status,
            Double totalAmount,
            String createdAt,
            String updatedAt
    ) {}

    GetPurchaseOrderResult handle(GetPurchaseOrderQuery query);
}
