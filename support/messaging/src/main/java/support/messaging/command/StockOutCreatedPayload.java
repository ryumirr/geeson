package support.messaging.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockOutCreatedPayload {

    private Long movementId;        // stock_movements.movement_id
    private Long inventoryId;       // stock_movements.inventory_id
    private String movementType;    // should always be "OUT"
    private Integer quantity;       // stock_movements.quantity
    private String referenceId;     // stock_movements.reference_id
    private String description;     // optional
}
