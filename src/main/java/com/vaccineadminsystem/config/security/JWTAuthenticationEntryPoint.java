package com.vaccineadminsystem.config.security;

import com.vaccineadminsystem.util.ErrorMess;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMess.JWT_PROVIDE);

    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("error");
        super.afterPropertiesSet();
    }
}
