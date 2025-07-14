package app.order.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentCommand {
    private Long orderId;
    private String carrier;
    private String trackingNumber;
}