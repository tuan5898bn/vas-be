package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.common.CommonService;
import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.dto.JWTRequest;
import com.vaccineadminsystem.dto.JWTResponse;
import com.vaccineadminsystem.dto.JWTUserDetails;
import com.vaccineadminsystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class JWTAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CommonService commonService;



    @PostMapping("/authentication")
    public ResponseEntity<JWTResponse> authenticate(
        @Valid @RequestBody JWTRequest jwtRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                jwtRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =
            jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new ResponseEntity<>(new JWTResponse(token), HttpStatus.OK);
    }


    @GetMapping("/current-logged")
    public ResponseEntity<EmployeeDto> getCurrentLogged() {
        JWTUserDetails jwtUserDetails = commonService.getCurrentLogin();
        EmployeeDto e = null;
        if (jwtUserDetails != null) {
            e = jwtUserDetails.getUser();
        }
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

}
