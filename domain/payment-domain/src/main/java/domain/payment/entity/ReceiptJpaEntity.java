package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import support.uuid.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptJpaEntity {
    @Id
    private Long receiptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    private String receiptUrl;
    private LocalDateTime issuedAt;
}
