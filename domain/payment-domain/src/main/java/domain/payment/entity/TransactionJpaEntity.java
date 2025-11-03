package domain.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import support.constants.payment.TransactionResultCode;
import support.constants.payment.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TransactionJpaEntity {
    @Id
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id")
    private PaymentGatewayJpaEntity gateway;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    private String pgOrderId;
    private String pgTransactionId;

    @Enumerated(EnumType.STRING)
    private TransactionResultCode resultCode;
    private String resultMessage;
    private LocalDateTime transactionTime;

    public void transactionSucceed(String resultMessage) {
        this.resultCode = TransactionResultCode.SUCCESS;
        this.resultMessage = resultMessage;
    }

    public void transactionFailed(String resultMessage) {
        this.resultCode = TransactionResultCode.FAILED;
        this.resultMessage = resultMessage;
    }

    public Boolean isCompleted() {
        return this.resultCode != TransactionResultCode.PENDING;
    }
}