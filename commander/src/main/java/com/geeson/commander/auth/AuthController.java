package com.geeson.commander.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RestClient restClient = RestClient.builder().build();

    @Value("${host.auth}")
    private String authHost;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<LoginResponse> response = restClient
                .post()
                .uri(authHost + "/api/v1/auth/login")
                .body(loginRequest)
                .retrieve()
                .toEntity(LoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Store token in session
                session.setAttribute("token", response.getBody().token());
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials");
                return "redirect:/auth/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Authentication failed: " + e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
