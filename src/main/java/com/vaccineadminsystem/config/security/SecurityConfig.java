package com.vaccineadminsystem.config.security;

import com.vaccineadminsystem.exception.handle.CustomAccessDeniedHandle;
import com.vaccineadminsystem.service.UserDetailsServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private JWTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandle accessDeniedHandle;

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandle).authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authentication").permitAll()
                .antMatchers(HttpMethod.POST, "/create-admin").permitAll()
                .antMatchers(HttpMethod.POST, "/employees").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/employees").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/employees/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, "/save-image").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST,"/addVaccineType").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT,"/updateVaccineType").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT,"/makeInActiveVaccineType").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST,"/news").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT,"/news/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE,"/news").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST,"/vaccines").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST,"/vaccines/import-file").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT,"/vaccines","/vaccines/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE,"/vaccines/**").hasRole(ADMIN)
                .antMatchers("/image/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthorizationFilter());
    }
}
