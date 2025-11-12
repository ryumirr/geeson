package app.inventory.port.in;

public interface GetWarehouseUseCase {

    record GetWarehouseQuery(Long warehouseId) {}

    record GetWarehouseResult(
            Long warehouseId,
            String name,
            String location,
            Integer capacity,
            String createdAt,
            String updatedAt
    ) {}

    GetWarehouseResult handle(GetWarehouseQuery query);
}
