package grpc.client.dto;

public record FailedItem(Long productId, Long warehouseId, int requested, int available) {}
