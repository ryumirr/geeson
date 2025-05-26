package support.webapi.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

public class TraceIdApiInterceptor implements HandlerInterceptor {
    private final String TRACE_ID_KEY = "traceId";
    private final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Trace ID 생성 (없으면 새로 생성)
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString(); // Trace ID가 없다면 새로 생성
        }

        MDC.put(TRACE_ID_KEY, traceId);

        // Trace ID를 요청 속성에 저장
        request.setAttribute(TRACE_ID_KEY, traceId);

        // 응답 헤더에 Trace ID 추가
        response.setHeader(TRACE_ID_HEADER, traceId);

        return true;
    }
}