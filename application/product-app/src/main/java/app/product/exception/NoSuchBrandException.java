package app.product.exception;

public class NoSuchBrandException extends RuntimeException {
    public NoSuchBrandException(String message) {
        super(message);
    }
}
