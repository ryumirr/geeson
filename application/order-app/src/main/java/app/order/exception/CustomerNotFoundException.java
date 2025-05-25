package app.order.exception;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private final String traceId = "";
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
