package app.inventory.port.in;

public interface CreateWarehouseUseCase {

    /**
     * 창고 생성 명령
     */
    record CreateWarehouseCommand(
            String name,
            String location,
            Integer capacity
    ) {}

    /**
     * 창고 생성 결과 DTO
     */
    record CreateWarehouseResult(
            Long warehouseId,
            String name,
            String location,
            Integer capacity,
            String createdAt,
            String updatedAt
    ) {}

    CreateWarehouseResult handle(CreateWarehouseCommand command);
}
