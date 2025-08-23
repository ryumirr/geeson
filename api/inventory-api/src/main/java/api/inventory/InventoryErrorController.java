package api.inventory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/error")
// HTTP 전용
public class InventoryErrorController implements ErrorController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        int statusCode = getStatusCode(request);
        String message = getCustomMessage(statusCode);
        String requestUri = getRequestUri(request);

        log.warn("Error occurred: status={}, uri={}", statusCode, requestUri);

        Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception != null) {
            log.error("Exception stacktrace:", exception);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", statusCode);
        body.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        body.put("message", message);
        body.put("path", requestUri);

        return ResponseEntity.status(statusCode).body(body);
    }

    private int getStatusCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return (status != null) ? Integer.parseInt(status.toString()) : 500;
    }

    private String getRequestUri(HttpServletRequest request) {
        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        return (uri != null) ? uri.toString() : "Unknown URI";
    }

    private String getCustomMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "잘못된 요청입니다. 요청 내용을 확인해주세요.";
            case 401 -> "인증되지 않은 사용자입니다. 로그인 후 이용해주세요.";
            case 403 -> "접근 권한이 없습니다.";
            case 404 -> "요청한 리소스를 찾을 수 없습니다.";
            case 500 -> "서버 내부 오류가 발생했습니다. 관리자에게 문의해주세요.";
            default -> "예상치 못한 오류가 발생했습니다.";
        };
    }
}
