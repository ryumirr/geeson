package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentMethodJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long methodId;

    private Long customerId;
    private String type; // CARD, ACCOUNT, MOBILE, etc
    private String cardCode;
    private String provider;
    private String maskedNumber;
    private LocalDate expirationDate;
    private String billingKey;
    private LocalDateTime createdAt;
}

