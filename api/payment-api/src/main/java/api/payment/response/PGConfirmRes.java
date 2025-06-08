package api.payment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PGConfirmRes(
      String mId,
      String lastTransactionKey,
      String paymentKey,
      String orderId,
      String orderName,
      Integer taxExemptionAmount,
      String status,
      String requestedAt,
      String approvedAt,
      Boolean useEscrow,
      Boolean cultureExpense,
      Card card,
      String virtualAccount,
      String transfer,
      String mobilePhone,
      String giftCertificate,
      String cashReceipt,
      String cashReceipts,
      String discount,
      String cancels,
      String secret,
      String type,
      EasyPay easyPay,
      String country,
      Failure failure,
      Boolean isPartialCancelable,
      Receipt receipt,
      Checkout checkout,
      String currency,
      BigDecimal totalAmount,
      BigDecimal balanceAmount,
      BigDecimal suppliedAmount,
      BigDecimal vat,
      BigDecimal taxFreeAmount,
      String metadata,
      String method,
      String version
) {
    public record Card(
        String issuerCode,
        String acquirerCode,
        String number,
        Integer installmentPlanMonths,
        Boolean isInterestFree,
        String interestPayer,
        String approveNo,
        Boolean useCardPoint,
        String cardType,
        String ownerType,
        String acquireStatus,
        BigDecimal amount
    ) {}

    public record EasyPay (
            String provider,
            BigDecimal amount,
            BigDecimal discountAmount
    ) {}

    public record Receipt (
        String url
    ) {}

    public record Checkout (
        String url
    ) {}

    public record Failure (
        String code,
        String message
    ) {}
}
