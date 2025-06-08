package support.masking;

public class CardMasker {
    public static String mask(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 10) {
            throw new IllegalArgumentException("카드 번호는 최소 10자리 이상이어야 합니다.");
        }

        String start = cardNumber.substring(0, 6);
        String end = cardNumber.substring(cardNumber.length() - 4);
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < cardNumber.length() - 10; i++) {
            masked.append("*");
        }

        return start + masked + end;
    }
}
