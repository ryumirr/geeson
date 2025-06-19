package com.geeson.commander.auth;

public record LoginRequest(
    String username,
    String password
) {
}