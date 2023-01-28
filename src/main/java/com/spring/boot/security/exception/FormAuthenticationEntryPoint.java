package com.spring.boot.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.common.presentation.response.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ApiResult<?> AUTHENTICATION_ERROR = ApiResult.error("Authentication error", HttpStatus.UNAUTHORIZED);
    private final ObjectMapper om;

    public FormAuthenticationEntryPoint(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(AUTHENTICATION_ERROR));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
