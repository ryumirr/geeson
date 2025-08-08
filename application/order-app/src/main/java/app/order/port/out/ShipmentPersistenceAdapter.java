package app.order.port.out;

import app.order.port.in.CreateShipmentUseCase.CreateShipmentCommand;
import app.order.port.in.GetShipmentUseCase.GetShipmentResult;
import domain.order.entity.ShipmentJpaEntity;
import grpc.shipment.GetShipmentRequest;
import grpc.shipment.GetShipmentResponse;
import grpc.shipment.ShipmentServiceGrpc.ShipmentServiceBlockingStub;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import domain.order.repository.ProductOrderRepository;
import domain.order.repository.ShipmentRepository;
import domain.order.entity.ShipmentJpaEntity;
import domain.order.entity.ProductOrderJpaEntity;

@Component
@RequiredArgsConstructor
/**
 * Order 내부용 어댑터
 */
public class ShipmentPersistenceAdapter implements ShipmentPort {

    private final ProductOrderRepository orderRepo;
    private final ShipmentRepository shipmentRepo;

    @GrpcClient("shipment")
    private ShipmentServiceBlockingStub shipmentStub;

    @Override
    public ShipmentJpaEntity createShipment(CreateShipmentCommand command) {
        ProductOrderJpaEntity order = orderRepo.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<ShipmentJpaEntity> existing = shipmentRepo.findByOrderId(command.orderId());
        Optional<ShipmentJpaEntity> matched = existing.stream()
                .filter(s -> s.getTrackingNumber().equals(command.trackingNumber()))
                .findFirst();

        return matched.orElseGet(() ->
                shipmentRepo.save(ShipmentJpaEntity.from(command.trackingNumber(), order))
        );
    }

    @Override
    public GetShipmentResult getShipmentById(Long shipmentId) {
        GetShipmentRequest request = GetShipmentRequest.newBuilder()
                .setShipmentId(shipmentId)
                .build();

        GetShipmentResponse response = shipmentStub.getShipment(request);

        return new GetShipmentResult(
                response.getShipmentId(),
                response.getOrderId(),
                response.getTrackingNumber(),
                response.getStatus(),
                response.getShippedDate(),
                response.getDeliveredDate(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }

    private LocalDateTime parse(String str) {
        return str != null && !str.isEmpty() ? LocalDateTime.parse(str) : null;
    }
}
