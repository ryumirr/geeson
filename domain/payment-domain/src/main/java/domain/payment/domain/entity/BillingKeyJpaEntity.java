package domain.payment.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing_keys")
public class BillingKeyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingKeyId;
    private Long customerId;
    private String provider;
    private String token;
    private LocalDate expiresAt;
    private String status;
    private LocalDateTime createdAt;
}