package rdb.storage.payment.core.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id")
    private PaymentGatewayJpaEntity gateway;

    private String transactionType;
    private BigDecimal amount;
    private String pgTransactionId;
    private String resultCode;
    private String resultMessage;
    private LocalDateTime transactionTime;
}