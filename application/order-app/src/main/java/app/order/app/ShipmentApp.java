package app.order.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import module.enums.ShipmentStatus;
import app.order.port.in.CreateShipmentUseCase;
import app.order.port.in.GetShipmentUseCase;

import java.util.List;
import java.util.Optional;

import domain.order.entity.ProductOrderJpaEntity;
import domain.order.entity.ShipmentJpaEntity;
import domain.order.repository.ShipmentRepository;
import domain.order.repository.ProductOrderRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentApp implements CreateShipmentUseCase, GetShipmentUseCase{
    private final ShipmentRepository shipmentRepo;
    private final ProductOrderRepository orderRepo;

    @Override
    public CreateShipmentResult createShipment(CreateShipmentCommand command) {
        ProductOrderJpaEntity order = orderRepo.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<ShipmentJpaEntity> shipments = shipmentRepo.findByOrderId(command.orderId());
        Optional<ShipmentJpaEntity> existing = shipments.stream()
                .filter(s -> s.getTrackingNumber().equals(command.trackingNumber()))
                .findFirst();

        if (existing.isPresent()) {
            // 이미 존재하는 배송 정보가 있는 경우, 존재하는 배송 정보를 반환
            ShipmentJpaEntity existingShipment = existing.get();
            return new CreateShipmentResult(
                existingShipment.getShipmentId(),
                existingShipment.getOrder().getOrderId(),
                existingShipment.getTrackingNumber(),
                existingShipment.getStatus(),
                existingShipment.getShippedDate().toString(),
                existingShipment.getDeliveredDate().toString(),
                existingShipment.getCreatedAt().toString(),
                existingShipment.getUpdatedAt().toString()
            );            
        } else {
            // 새로 생성 하는 경우
            ShipmentJpaEntity shipment = ShipmentJpaEntity.from(command.trackingNumber(), order);
            ShipmentJpaEntity result = shipmentRepo.save(shipment);
            return new CreateShipmentResult(
                result.getShipmentId(),
                result.getOrder().getOrderId(),
                result.getTrackingNumber(),
                result.getStatus(),
                result.getShippedDate().toString(),
                result.getDeliveredDate().toString(),
                result.getCreatedAt().toString(),
                result.getUpdatedAt().toString()
            );
        }
    }

    @Override
    public GetShipmentResult getShipmentById(GetShipmentCommand command) {
        ShipmentJpaEntity shipment = shipmentRepo.findByShipmentId(command.shipmentId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found: " + command.shipmentId()));

        return new GetShipmentResult(
                shipment.getShipmentId(),
                shipment.getOrder().getOrderId(),
                shipment.getTrackingNumber(),
                shipment.getStatus(),
                shipment.getShippedDate() != null ? shipment.getShippedDate().toString() : null,
                shipment.getDeliveredDate() != null ? shipment.getDeliveredDate().toString() : null,
                shipment.getCreatedAt().toString(),
                shipment.getUpdatedAt().toString());
    }

    public List<ShipmentJpaEntity> getShipmentsByOrderId(Long orderId) {
        return shipmentRepo.findByOrderId(orderId);
    }

    public void updateStatus(Long shipmentId, ShipmentStatus status) {
        shipmentRepo.updateStatus(shipmentId, status);
    }

    public List<ShipmentJpaEntity> listAll() {
        return shipmentRepo.findAll();
    }

    public Optional<ShipmentJpaEntity> getByTrackingNumber(String trackingNumber) {
        return shipmentRepo.findByTrackingNumber(trackingNumber);
    }
}
