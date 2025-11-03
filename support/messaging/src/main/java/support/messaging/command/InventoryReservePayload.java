package support.messaging.command;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryReservePayload extends CommandPayload {
    private String reservationId;
    private String inventoryId;
    private String orderId;
    private Integer quantity;
    private Integer expiresAt;
}
