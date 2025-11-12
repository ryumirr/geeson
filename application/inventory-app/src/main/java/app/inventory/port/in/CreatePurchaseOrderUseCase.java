package app.inventory.port.in;

/**
 * 발주 생성 UseCase (입력 포트)
 */
public interface CreatePurchaseOrderUseCase {

    /**
     * 발주 생성 명령
     */
    record CreatePurchaseOrderCommand(
            Long supplierId,
            Long warehouseId,
            String orderDate,
            String status,
            Double totalAmount
    ) {}

    /**
     * 발주 생성 결과 DTO
     */
    record CreatePurchaseOrderResult(
            Long purchaseOrderId,
            Long supplierId,
            Long warehouseId,
            String orderDate,
            String status,
            Double totalAmount,
            String createdAt,
            String updatedAt
    ) {}

    CreatePurchaseOrderResult handle(CreatePurchaseOrderCommand command);
}
