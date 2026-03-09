package com.typeb.assignment.infrastructure.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String traceId = MDC.get("traceId");

        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        log.info("📥 REQUEST: [traceId={}] Method={}, URI={}, Params={}, Headers={}",
                traceId,
                request.getMethod(),
                request.getRequestURI(),
                request.getParameterMap(),
                Collections.list(request.getHeaderNames())
                        .stream()
                        .collect(Collectors.toMap(h -> h, request::getHeader))
        );

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            String responseBody = "";
            try {
                byte[] content = wrappedResponse.getContentAsByteArray();
                if (content.length > 0) {
                    responseBody = new String(content, StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                log.error("Failed to read response body for logging", e);
            }

            log.info("📤 RESPONSE: [traceId={}] Status={}, Time={}ms, Headers={}, Body={}",
                    traceId,
                    wrappedResponse.getStatus(),
                    duration,
                    wrappedResponse.getHeaderNames().stream()
                            .collect(Collectors.toMap(h -> h, wrappedResponse::getHeader)),
                    responseBody
            );

            wrappedResponse.copyBodyToResponse();
        }
    }
}