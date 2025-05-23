package app.payment.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
public class PaymentMethodJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodId;

    private Long customerId;
    private String type; // CARD, ACCOUNT, MOBILE, etc
    private String provider;
    private String maskedNumber;
    private LocalDate expirationDate;
    private String billingKey;
    private LocalDateTime createdAt;
}

