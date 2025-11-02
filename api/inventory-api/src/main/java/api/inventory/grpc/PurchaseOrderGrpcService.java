package api.inventory.grpc;

import grpc.purchaseorder.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;

import domain.inventory.domain.entity.PurchaseOrderJpaEntity;
import domain.inventory.domain.entity.SupplierJpaEntity;
import domain.inventory.domain.entity.WarehouseJpaEntity;
import domain.inventory.domain.repository.PurchaseOrderRepository;
import domain.inventory.domain.repository.SupplierRepository;
import domain.inventory.domain.repository.WarehouseRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@GrpcService
@RequiredArgsConstructor
public class PurchaseOrderGrpcService extends PurchaseOrderServiceGrpc.PurchaseOrderServiceImplBase {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /** 발주 등록 */
    @Override
    public void addPurchaseOrder(AddPurchaseOrderRequest request,
                                 StreamObserver<AddPurchaseOrderResponse> responseObserver) {
        try {
            SupplierJpaEntity supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found: " + request.getSupplierId()));
            WarehouseJpaEntity warehouse = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found: " + request.getWarehouseId()));

            PurchaseOrderJpaEntity entity = PurchaseOrderJpaEntity.create(
                    supplier,
                    warehouse,
                    LocalDateTime.parse(request.getOrderDate(), FORMATTER),
                    request.getStatus(),
                    BigDecimal.valueOf(request.getTotalAmount())
            );

            PurchaseOrderJpaEntity saved = purchaseOrderRepository.save(entity);

            PurchaseOrder po = PurchaseOrder.newBuilder()
                    .setPurchaseOrderId(saved.getPurchaseOrderId())
                    .setSupplierId(saved.getSupplier().getSupplierId())
                    .setWarehouseId(saved.getWarehouse().getWarehouseId())
                    .setOrderDate(saved.getOrderDate().format(FORMATTER))
                    .setStatus(saved.getStatus())
                    .setTotalAmount(saved.getTotalAmount().doubleValue())
                    // createdAt/updatedAt 필드가 엔티티에 없으므로, 우선 orderDate/now 로 매핑
                    .setCreatedAt(saved.getOrderDate().format(FORMATTER))
                    .setUpdatedAt(saved.getOrderDate().format(FORMATTER))
                    .build();

            responseObserver.onNext(AddPurchaseOrderResponse.newBuilder().setPurchaseOrder(po).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /** 발주 단건 조회 */
    @Override
    public void selectPurchaseOrder(SelectPurchaseOrderRequest request,
                                    StreamObserver<SelectPurchaseOrderResponse> responseObserver) {
        try {
            PurchaseOrderJpaEntity entity = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PurchaseOrder not found: " + request.getPurchaseOrderId()));

            PurchaseOrder po = PurchaseOrder.newBuilder()
                    .setPurchaseOrderId(entity.getPurchaseOrderId())
                    .setSupplierId(entity.getSupplier().getSupplierId())
                    .setWarehouseId(entity.getWarehouse().getWarehouseId())
                    .setOrderDate(entity.getOrderDate().format(FORMATTER))
                    .setStatus(entity.getStatus())
                    .setTotalAmount(entity.getTotalAmount().doubleValue())
                    .setCreatedAt(entity.getOrderDate().format(FORMATTER))
                    .setUpdatedAt(entity.getOrderDate().format(FORMATTER))
                    .build();

            responseObserver.onNext(SelectPurchaseOrderResponse.newBuilder().setPurchaseOrder(po).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /** 다중 발주 조회 */
    @Override
    public void selectPurchaseOrders(SelectPurchaseOrdersRequest request,
                                     StreamObserver<SelectPurchaseOrdersResponse> responseObserver) {
        try {
            var entities = purchaseOrderRepository.findAllById(request.getPurchaseOrderIdsList());
            SelectPurchaseOrdersResponse.Builder builder = SelectPurchaseOrdersResponse.newBuilder();

            for (PurchaseOrderJpaEntity e : entities) {
                PurchaseOrder po = PurchaseOrder.newBuilder()
                        .setPurchaseOrderId(e.getPurchaseOrderId())
                        .setSupplierId(e.getSupplier().getSupplierId())
                        .setWarehouseId(e.getWarehouse().getWarehouseId())
                        .setOrderDate(e.getOrderDate().format(FORMATTER))
                        .setStatus(e.getStatus())
                        .setTotalAmount(e.getTotalAmount().doubleValue())
                        .setCreatedAt(e.getOrderDate().format(FORMATTER))
                        .setUpdatedAt(e.getOrderDate().format(FORMATTER))
                        .build();
                builder.addPurchaseOrders(po);
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    /** 발주 상태 갱신 */
    @Override
    public void updatePurchaseOrderStatus(UpdatePurchaseOrderStatusRequest request,
                                          StreamObserver<UpdatePurchaseOrderStatusResponse> responseObserver) {
        try {
            PurchaseOrderJpaEntity entity = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PurchaseOrder not found: " + request.getPurchaseOrderId()));

            // ✅ DDD 행동 메서드로 상태 변경 (setter 금지)
            entity.updateStatus(request.getStatus());
            PurchaseOrderJpaEntity updated = purchaseOrderRepository.save(entity);

            PurchaseOrder po = PurchaseOrder.newBuilder()
                    .setPurchaseOrderId(updated.getPurchaseOrderId())
                    .setSupplierId(updated.getSupplier().getSupplierId())
                    .setWarehouseId(updated.getWarehouse().getWarehouseId())
                    .setOrderDate(updated.getOrderDate().format(FORMATTER))
                    .setStatus(updated.getStatus())
                    .setTotalAmount(updated.getTotalAmount().doubleValue())
                    .setCreatedAt(updated.getOrderDate().format(FORMATTER))
                    .setUpdatedAt(LocalDateTime.now().format(FORMATTER))
                    .build();

            responseObserver.onNext(UpdatePurchaseOrderStatusResponse.newBuilder()
                    .setSuccess(true)
                    .setPurchaseOrder(po)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
