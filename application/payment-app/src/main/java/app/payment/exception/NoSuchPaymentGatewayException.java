package app.payment.exception;

public class NoSuchPaymentGatewayException extends RuntimeException {
    public NoSuchPaymentGatewayException(String message) {
        super(message);
    }
}
