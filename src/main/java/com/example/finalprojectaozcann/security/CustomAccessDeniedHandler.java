package com.example.finalprojectaozcann.security;

import com.example.finalprojectaozcann.exception.BaseException;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {



    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("FORBIDDEN {}", accessDeniedException.getMessage());
        //TODO message send
        response.sendError(HttpServletResponse.SC_FORBIDDEN,"message");
        throw new AccessDeniedException("sess");
    }
}
