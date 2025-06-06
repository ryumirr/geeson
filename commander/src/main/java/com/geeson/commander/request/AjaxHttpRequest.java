package com.geeson.commander.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AjaxHttpRequest (
    String method,
    String url,
    List<HttpHeader> headers,
    Object body
) {
    public record HttpHeader (
        String key,
        String value
    ) {}
}
