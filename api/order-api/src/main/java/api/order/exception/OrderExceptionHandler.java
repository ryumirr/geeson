package api.order.exception;

import app.order.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.webapi.error.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class OrderExceptionHandler {
    @Value("${api.version}")
    private String version;

    @ExceptionHandler(ResponseMappingException.class)
    public ResponseEntity<ErrorResponse> handleResponseMappingException(ResponseMappingException e) {
        log.error("ResponseMappingException: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponse.create(version, e.getTraceId(), OrderErrorCode.PROVIDER_ERROR)
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResponseMappingException(CustomerNotFoundException e) {
        log.error("CustomerNotFoundException: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(
            ErrorResponse.create(version, e.getTraceId(), OrderErrorCode.PROVIDER_ERROR)
        );
    }
}
