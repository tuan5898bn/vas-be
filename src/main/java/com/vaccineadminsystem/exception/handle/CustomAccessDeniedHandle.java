package com.vaccineadminsystem.exception.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccineadminsystem.util.ErrorMess;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandle implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e)
        throws IOException, ServletException {
        Map<String, Object> message = new HashMap<>();
        message.put("status", HttpStatus.FORBIDDEN.value());
        message.put("message", ErrorMess.ACCESS_DENIED);
        message.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
        ResponseEntity<?> resMess = new ResponseEntity<>(message,HttpStatus.FORBIDDEN);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, resMess);
        out.flush();
    }
}
