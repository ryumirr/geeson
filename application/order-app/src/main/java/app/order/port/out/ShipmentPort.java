package app.order.port.out;

import org.springframework.stereotype.Component;

import app.order.port.in.CreateShipmentUseCase.CreateShipmentCommand;
import domain.order.entity.ShipmentJpaEntity;

@Component
public interface ShipmentPort {

    ShipmentJpaEntity createShipment(CreateShipmentCommand command);

    ShipmentJpaEntity getShipmentById(Long shipmentId);
}
