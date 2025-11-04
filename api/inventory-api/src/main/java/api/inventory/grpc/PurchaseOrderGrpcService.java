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
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PurchaseOrderGrpcService extends PurchaseOrderServiceGrpc.PurchaseOrderServiceImplBase {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // ==========================
    // 신규 발주 등록
    // ==========================
    @Override
    public void addPurchaseOrder(AddPurchaseOrderRequest request,
                                 StreamObserver<AddPurchaseOrderResponse> responseObserver) {
        try {
            SupplierJpaEntity supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found: " + request.getSupplierId()));
            WarehouseJpaEntity warehouse = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found: " + request.getWarehouseId()));

            LocalDateTime orderDate = request.getOrderDate().isEmpty()
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(request.getOrderDate(), FORMATTER);

            PurchaseOrderJpaEntity entity = PurchaseOrderJpaEntity.create(
                    supplier,
                    warehouse,
                    orderDate,
                    "PENDING",
                    BigDecimal.valueOf(request.getTotalAmount())
            );

            PurchaseOrderJpaEntity saved = purchaseOrderRepository.save(entity);

            PurchaseOrder po = mapToProto(saved);
            responseObserver.onNext(AddPurchaseOrderResponse.newBuilder().setPurchaseOrder(po).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // ==========================
    // 단일 발주 조회 (ID or supplier+warehouse)
    // ==========================
    @Override
    public void getPurchaseOrder(GetPurchaseOrderRequest request,
                                 StreamObserver<GetPurchaseOrderResponse> responseObserver) {
        try {
            PurchaseOrderJpaEntity entity;

            // 1️⃣ ID로 조회
            if (request.hasPurchaseOrderId()) {
                entity = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                        .orElseThrow(() -> new RuntimeException("PurchaseOrder not found: " + request.getPurchaseOrderId()));
            }
            // 2️⃣ supplier_id + warehouse_id 복합키로 조회
            else if (request.hasKey()) {
                long supplierId = request.getKey().getSupplierId();
                long warehouseId = request.getKey().getWarehouseId();
                entity = purchaseOrderRepository
                        .findBySupplier_SupplierIdAndWarehouse_WarehouseId(supplierId, warehouseId)
                        .orElseThrow(() -> new RuntimeException(
                                String.format("PurchaseOrder not found for supplier=%d, warehouse=%d", supplierId, warehouseId)
                        ));
            } else {
                throw new RuntimeException("No identifier provided");
            }

            PurchaseOrder po = mapToProto(entity);
            responseObserver.onNext(GetPurchaseOrderResponse.newBuilder().setPurchaseOrder(po).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // ==========================
    // 다중 발주 조회 (필터 지원)
        // ==========================
    @Override
    public void listPurchaseOrders(ListPurchaseOrdersRequest request,
                                StreamObserver<ListPurchaseOrdersResponse> responseObserver) {
        try {
            List<PurchaseOrderJpaEntity> entities = new java.util.ArrayList<>();

            if (!request.getPurchaseOrderIdsList().isEmpty()) {
                entities = purchaseOrderRepository.findAllById(request.getPurchaseOrderIdsList());
            } 
            else if (request.getSupplierId() != 0 && request.getWarehouseId() != 0) {
                purchaseOrderRepository
                        .findBySupplier_SupplierIdAndWarehouse_WarehouseId(
                                request.getSupplierId(), request.getWarehouseId()
                        )
                        .ifPresent(entities::add); // ✅ Optional → List 변환
            } 
            else if (request.getSupplierId() != 0) {
                entities = purchaseOrderRepository.findBySupplier_SupplierId(request.getSupplierId());
            } 
            else if (request.getWarehouseId() != 0) {
                entities = purchaseOrderRepository.findByWarehouse_WarehouseId(request.getWarehouseId());
            } 
            else {
                entities = purchaseOrderRepository.findAll();
            }

            // 결과 매핑
            ListPurchaseOrdersResponse.Builder builder = ListPurchaseOrdersResponse.newBuilder();
            entities.forEach(e -> builder.addPurchaseOrders(mapToProto(e)));

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // ==========================
    // 발주 상태 갱신
    // ==========================
    @Override
    public void updatePurchaseOrderStatus(UpdatePurchaseOrderStatusRequest request,
                                          StreamObserver<UpdatePurchaseOrderStatusResponse> responseObserver) {
        try {
            PurchaseOrderJpaEntity entity = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PurchaseOrder not found: " + request.getPurchaseOrderId()));

            entity.updateStatus(request.getStatus());
            PurchaseOrderJpaEntity updated = purchaseOrderRepository.save(entity);

            PurchaseOrder po = mapToProto(updated);

            responseObserver.onNext(UpdatePurchaseOrderStatusResponse.newBuilder()
                    .setSuccess(true)
                    .setPurchaseOrder(po)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // ==========================
    // 공통 변환 메서드
    // ==========================
    private PurchaseOrder mapToProto(PurchaseOrderJpaEntity entity) {
        return PurchaseOrder.newBuilder()
                .setPurchaseOrderId(entity.getPurchaseOrderId())
                .setSupplierId(entity.getSupplier().getSupplierId())
                .setWarehouseId(entity.getWarehouse().getWarehouseId())
                .setOrderDate(entity.getOrderDate().format(FORMATTER))
                .setStatus(entity.getStatus())
                .setTotalAmount(entity.getTotalAmount().doubleValue())
                .setCreatedAt(entity.getOrderDate().format(FORMATTER))
                .setUpdatedAt(LocalDateTime.now().format(FORMATTER))
                .build();
    }
}
