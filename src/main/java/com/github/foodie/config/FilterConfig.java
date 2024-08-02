package com.github.foodie.config;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.UUID;

@Configuration
@Slf4j
public class FilterConfig implements Filter {
    private final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info("Intercepted coming request for setting MDC context");
        final String correlationId = getCorrelationIdFromHeader(httpServletRequest);
        log.info("Correlation id is set as: " + correlationId);
        MDC.put(CORRELATION_ID, correlationId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = generateUniqueCorrelationId();
        }
        return correlationId;
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
