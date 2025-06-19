package com.geeson.commander;

import com.geeson.commander.request.AjaxHttpRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@Slf4j
public class CommandController {
    private final RestClient restClient = RestClient.builder().build();

    @Value("${host.order}")
    private String orderHost;

    @PostMapping("/request")
    public ResponseEntity<Object> getRequest(@RequestBody AjaxHttpRequest requestBody, HttpSession session) {
        // Check if user is authenticated
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        log.info("request: {}", requestBody);

        // Add Authorization header with JWT token
        ResponseEntity<Object> entity = restClient
            .method(HttpMethod.valueOf(requestBody.method().toUpperCase()))
            .uri(requestBody.url())
            .headers((v) -> {
                // Add existing headers
                requestBody.headers().forEach((h) -> v.add(h.key(), h.value()));
                // Add Authorization header with JWT token
                v.add("Authorization", "Bearer " + token);
            })
            .body(requestBody.body())
            .retrieve()
            .toEntity(Object.class);

        log.info("response: {}", entity);
        return entity;
    }
}
