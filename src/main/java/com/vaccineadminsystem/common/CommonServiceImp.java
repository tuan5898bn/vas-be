package com.vaccineadminsystem.common;

import com.vaccineadminsystem.dto.JWTUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImp implements CommonService {


    @Override
    public JWTUserDetails getCurrentLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JWTUserDetails) authentication.getPrincipal();
    }

    @Override
    public String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
    }


}
