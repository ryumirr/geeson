package app.order.app;

import domain.order.entity.ProductOrderJpaEntity;
import domain.order.repository.ProductOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import app.order.command.OrderRegisterCommand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import grpc.client.InventoryGrpcClient;
import grpc.inventory.SelectInventoryResponse;
import grpc.inventory.InventoryCheck;
import grpc.inventory.SelectInventoriesRequest;
import grpc.inventory.InventoryResult;
import grpc.client.dto.OrderRegister;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderListApp {

    private final ProductOrderRepository productOrderRepository;
    private final InventoryGrpcClient inventoryGrpcClient;

    public List<ProductOrderJpaEntity> getAllOrders(int page, int size) {
        return productOrderRepository.getAllOrders(page, size);
    }

    public List<ProductOrderJpaEntity> getOrdersByCustomerId(Long customerId) {
        return productOrderRepository.findByCustomerId(customerId);
    }

    /**
     * gRPC inventory 단건 조회 테스트
     */
    public SelectInventoryResponse getInventory(Long productId, int quantity) {
        try {
            return inventoryGrpcClient.selectInventory(productId, quantity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching selectInventory", e);
        }
    }

    /**
     * gRPC inventory 다건 조회 테스트
     */
    public Map<Long, Boolean> checkInventories(List<OrderRegisterCommand.OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return Map.of();
        }

        List<OrderRegister> params = items.stream()
                .map(i -> new OrderRegister(i.productId(), i.quantity()))
                .toList();

        return inventoryGrpcClient.checkInventories(params);
    }
}
