package com.spring.boot.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.common.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormAccessDeniedHandler implements AccessDeniedHandler {

    private final ApiResult<?> AUTHORIZED_ERROR = ApiResult.error("forbidden access error", HttpStatus.FORBIDDEN);
    private final ObjectMapper om;

    public FormAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("content-type", "application/json");
        response.getWriter().write(om.writeValueAsString(AUTHORIZED_ERROR));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
