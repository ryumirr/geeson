package support.webapi.error;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private final String version;
    private final String traceId;
    private final Body body;

    public static ErrorResponse create(String version, String traceId, ErrorCode errorCode) {
        return new ErrorResponse(
                version,
                traceId,
                new Body(errorCode.getCode(), errorCode.getMessage())
        );
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Body {
        private final String code;
        private final String message;
    }
}
