package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import support.constants.payment.VendorCode;
import support.uuid.UuidGenerator;

@Entity
@Table(name = "payment_gateways")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PaymentGatewayJpaEntity {
    @Id
    private Long gatewayId;
    private String name;
    private String apiUrl;
    @Enumerated(EnumType.STRING)
    private VendorCode vendorCode;
    private Boolean active;
}