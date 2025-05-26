package api.order.exception;

import lombok.Getter;

@Getter
public class ResponseMappingException extends RuntimeException {
    private final String traceId;
    public ResponseMappingException(String traceId, String message) {
        super(message);
        this.traceId = traceId;
    }
}
