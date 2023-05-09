package com.vaccineadminsystem.dto;

import javax.validation.constraints.NotEmpty;

public class JWTRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public JWTRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JWTRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
