package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_gateways")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PaymentGatewayJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gatewayId;
    private String name;
    private String apiUrl;
    private String vendorCode;
    private Boolean active;
}