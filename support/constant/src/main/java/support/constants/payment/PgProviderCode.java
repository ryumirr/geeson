package support.constants.payment;

public enum PgProviderCode {
    KB_CARD, TOSS_PAY, SAMSUNG_PAY, KAKAO_PAY;

    public VendorCode getVendor() {
        return switch (this) {
            case KB_CARD -> VendorCode.KCP;
            case TOSS_PAY -> VendorCode.TOSS;
            case SAMSUNG_PAY -> VendorCode.PAYPAL;
            case KAKAO_PAY -> VendorCode.KAKAO;
        };
    }
}
