package app.inventory.port.in;

/**
 * 발주 상태 갱신 UseCase (입력 포트)
 */
public interface UpdatePurchaseOrderStatusUseCase {

    record UpdatePurchaseOrderStatusCommand(
            Long purchaseOrderId,
            String status
    ) {}

    record UpdatePurchaseOrderStatusResult(
            boolean success,
            Long purchaseOrderId,
            String status,
            String updatedAt
    ) {}

    UpdatePurchaseOrderStatusResult handle(UpdatePurchaseOrderStatusCommand command);
}
