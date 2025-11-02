package grpc.client;

import grpc.purchaseorder.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.info("PurchaseOrderGrpcClient initialized");
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            log.info("PurchaseOrderGrpcClient channel shutdown");
        }
    }

    // ===== Add Purchase Order =====
    public AddPurchaseOrderResponse addPurchaseOrder(AddPurchaseOrderRequest request) {
        try {
            return purchaseOrderStub.addPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call addPurchaseOrder failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Select Single Purchase Order =====
    public SelectPurchaseOrderResponse selectPurchaseOrder(long purchaseOrderId) {
        try {
            SelectPurchaseOrderRequest request = SelectPurchaseOrderRequest.newBuilder()
                    .setPurchaseOrderId(purchaseOrderId)
                    .build();
            return purchaseOrderStub.selectPurchaseOrder(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call selectPurchaseOrder failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Select Multiple Purchase Orders =====
    public SelectPurchaseOrdersResponse selectPurchaseOrders(Iterable<Long> purchaseOrderIds) {
        try {
            SelectPurchaseOrdersRequest.Builder requestBuilder = SelectPurchaseOrdersRequest.newBuilder();
            purchaseOrderIds.forEach(requestBuilder::addPurchaseOrderIds);

            return purchaseOrderStub.selectPurchaseOrders(requestBuilder.build());
        } catch (StatusRuntimeException e) {
            log.error("gRPC call selectPurchaseOrders failed: {}", e.getStatus(), e);
            throw e;
        }
    }

    // ===== Update Purchase Order Status =====
    public UpdatePurchaseOrderStatusResponse updatePurchaseOrderStatus(long purchaseOrderId, String status) {
        try {
            UpdatePurchaseOrderStatusRequest request = UpdatePurchaseOrderStatusRequest.newBuilder()
                    .setPurchaseOrderId(purchaseOrderId)
                    .setStatus(status)
                    .build();

            return purchaseOrderStub.updatePurchaseOrderStatus(request);
        } catch (StatusRuntimeException e) {
            log.error("gRPC call updatePurchaseOrderStatus failed: {}", e.getStatus(), e);
            throw e;
        }
    }
}
