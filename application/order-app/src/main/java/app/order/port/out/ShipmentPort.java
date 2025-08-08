package app.order.port.out;

import app.order.port.in.CreateShipmentUseCase.CreateShipmentCommand;
import app.order.port.in.GetShipmentUseCase.GetShipmentResult;
import domain.order.entity.ShipmentJpaEntity;

public interface ShipmentPort {

    ShipmentJpaEntity createShipment(CreateShipmentCommand command);

    GetShipmentResult getShipmentById(Long shipmentId);
}
