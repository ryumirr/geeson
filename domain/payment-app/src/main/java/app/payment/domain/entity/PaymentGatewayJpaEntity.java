package app.payment.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_gateways")
public class PaymentGatewayJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gatewayId;
    private String name;
    private String apiUrl;
    private String vendorCode;
    private Boolean active;
}