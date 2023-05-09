package com.vaccineadminsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JWTUserDetails implements UserDetails {

    private final String id;

    private final String username;

    private final EmployeeDto user;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public JWTUserDetails(String id, String username, String password, List<String> roles,EmployeeDto employeeDto) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.user =employeeDto;
    }

    public String getId() {
        return id;
    }

    public EmployeeDto getUser() {
        return user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
