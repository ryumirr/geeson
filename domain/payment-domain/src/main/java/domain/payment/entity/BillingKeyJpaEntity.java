package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import support.uuid.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing_keys")
@NoArgsConstructor
@AllArgsConstructor
public class BillingKeyJpaEntity {
    @Id
    private Long billingKeyId;
    private Long customerId;
    private String provider;
    private String token;
    private LocalDate expiresAt;
    private String status;
    private LocalDateTime createdAt;
}