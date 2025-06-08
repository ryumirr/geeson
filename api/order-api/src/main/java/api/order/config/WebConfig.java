package api.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import support.webapi.interceptor.TraceIdApiInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdApiInterceptor())
            .addPathPatterns("/**");  // 모든 요청에 대해 인터셉터 적용
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 URL 경로에 대해
            .allowedOrigins("*")  // 모든 출처 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
            .allowedHeaders("*")  // 모든 요청 헤더 허용
            .allowCredentials(false)  // 자격 증명(쿠키 등)은 포함하지 않음
            .maxAge(3600);  // preflight 요청에 대한 캐시 시간 (초 단위)
    }
}
