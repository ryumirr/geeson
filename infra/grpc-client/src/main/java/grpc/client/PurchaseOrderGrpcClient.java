package grpc.client;

import grpc.purchaseorder.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PurchaseOrderGrpcClient {

    private ManagedChannel channel;
    private PurchaseOrderServiceGrpc.PurchaseOrderServiceBlockingStub purchaseOrderStub;

    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder
                .forAddress("grpc-server", 6565)
                .usePlaintext()
                .build();

        this.purchaseOrderStub = PurchaseOrderServiceGrpc.newBlockingStub(channel);
        log.info("✅ PurchaseOrderGrpcClient initialized");
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            log.info("🧹 PurchaseOrderGrpcClient channel shutdown");
        }
    }

    // ==========================
    // Add Purchase Order
    // ==========================
    public AddPurchaseOrderResponse addPurchaseOrder(AddPurchaseOrderRequest request) {
        try {
            return purchaseOrderStub.addPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call addPurchaseOrder failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ==========================
    // Get Single Purchase Order (by ID or composite key)
    // ==========================
    public GetPurchaseOrderResponse getPurchaseOrderById(long purchaseOrderId) {
        try {
            GetPurchaseOrderRequest request = GetPurchaseOrderRequest.newBuilder()
                    .setPurchaseOrderId(purchaseOrderId)
                    .build();
            return purchaseOrderStub.getPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call getPurchaseOrderById failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    public GetPurchaseOrderResponse getPurchaseOrder(GetPurchaseOrderRequest request) {
        try {
            return purchaseOrderStub.getPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call getPurchaseOrder failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    public GetPurchaseOrderResponse getPurchaseOrderByKey(long supplierId, long warehouseId) {
        try {
            PurchaseOrderKey key = PurchaseOrderKey.newBuilder()
                    .setSupplierId(supplierId)
                    .setWarehouseId(warehouseId)
                    .build();

            GetPurchaseOrderRequest request = GetPurchaseOrderRequest.newBuilder()
                    .setKey(key)
                    .build();

            return purchaseOrderStub.getPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call getPurchaseOrderByKey failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ==========================
    // List Purchase Orders (filter supported)
    // ==========================
    public ListPurchaseOrdersResponse listPurchaseOrders(List<Long> purchaseOrderIds, Long supplierId, Long warehouseId, String status) {
        try {
            ListPurchaseOrdersRequest.Builder requestBuilder = ListPurchaseOrdersRequest.newBuilder();

            if (purchaseOrderIds != null && !purchaseOrderIds.isEmpty()) {
                requestBuilder.addAllPurchaseOrderIds(purchaseOrderIds);
            }
            if (supplierId != null) requestBuilder.setSupplierId(supplierId);
            if (warehouseId != null) requestBuilder.setWarehouseId(warehouseId);
            if (status != null && !status.isBlank()) requestBuilder.setStatus(status);

            return purchaseOrderStub.listPurchaseOrders(requestBuilder.build());
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call listPurchaseOrders failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ==========================
    // Update Purchase Order Status
    // ==========================
    public UpdatePurchaseOrderStatusResponse updatePurchaseOrderStatus(long purchaseOrderId, String status) {
        try {
            UpdatePurchaseOrderStatusRequest request = UpdatePurchaseOrderStatusRequest.newBuilder()
                    .setPurchaseOrderId(purchaseOrderId)
                    .setStatus(status)
                    .build();

            return purchaseOrderStub.updatePurchaseOrderStatus(request);
        } catch (StatusRuntimeException e) {
            log.error("❌ gRPC call updatePurchaseOrderStatus failed: {}", e.getStatus(), e);
            throw e;
        }
    }
}
