package com.geeson.commander.auth;

public record LoginResponse(
    String token,
    String username
) {
}