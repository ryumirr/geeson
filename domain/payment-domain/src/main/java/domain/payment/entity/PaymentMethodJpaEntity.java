package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import support.constants.payment.PgProviderCode;
import support.uuid.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentMethodJpaEntity {
    @Id
    private Long methodId;

    private Long customerId;
    private String type; // CARD, ACCOUNT, MOBILE, etc
    private String cardCode;
    @Enumerated(EnumType.STRING)
    private PgProviderCode provider;
    private String maskedNumber;
    private LocalDate expirationDate;
    private String billingKey;
    private LocalDateTime createdAt;

    public void setPaymentResult(String type, String cardCode, PgProviderCode provider, String maskedNumber) {
        this.type = type;
        this.cardCode = cardCode;
        this.provider = provider;
        this.maskedNumber = maskedNumber;
    }

}

