package grpc.client.dto;

import java.util.List;

public record ReserveItemDto(Long productId, Long warehouseId, int quantity) {}
