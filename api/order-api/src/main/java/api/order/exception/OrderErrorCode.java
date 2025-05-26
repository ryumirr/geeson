package api.order.exception;

import lombok.AllArgsConstructor;
import support.webapi.error.ErrorCode;

@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ALREADY_PROCESSED_PAYMENT("400", "ALREADY_PROCESSED_PAYMENT"),
    PROVIDER_ERROR("400", "PROVIDER_ERROR"),
    EXCEED_MAX_CARD_INSTALLMENT_PLAN("400", "EXCEED_MAX_CARD_INSTALLMENT_PLAN"),
    INVALID_REQUEST("400", "INVALID_REQUEST")
    ;

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
