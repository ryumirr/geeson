package app.payment.exception;

public class TransactionAlreadyCompleteException extends RuntimeException {
    public TransactionAlreadyCompleteException(String message) {
        super(message);
    }
}
