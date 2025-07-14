package app.order.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import app.order.command.ShipmentCommand;
import app.order.exception.CustomerNotFoundException;

import module.enums.ShipmentStatus;
import java.util.List;
import java.util.Optional;

import domain.order.entity.ProductOrderJpaEntity;
import domain.order.entity.ShipmentJpaEntity;
import domain.order.repository.ShipmentRepository;
import domain.order.repository.ProductOrderRepository;

@Service
@RequiredArgsConstructor
public class ShipmentApp {

    private final ShipmentRepository shipmentRepo;
    private final ProductOrderRepository orderRepo;

    public ShipmentJpaEntity createShipment(ShipmentCommand command) {
        ProductOrderJpaEntity order = orderRepo.findById(command.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        List<ShipmentJpaEntity> shipments = shipmentRepo.findByOrderId(command.getOrderId());
        Optional<ShipmentJpaEntity> existing = shipments.stream()
                .filter(s -> s.getTrackingNumber().equals(command.getTrackingNumber()))
                .findFirst();

        if (existing.isPresent()) {
            return existing.get();
        }

        ShipmentJpaEntity shipment = ShipmentJpaEntity.from(command.getTrackingNumber(), order);
        return shipmentRepo.save(shipment); // 또는 create(shipment)
    }

    public ShipmentJpaEntity getShipmentById(Long id) {
        return shipmentRepo.findByShipmentId(id)
            .orElseThrow(() -> new IllegalArgumentException("Shipment not found: " + id));
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