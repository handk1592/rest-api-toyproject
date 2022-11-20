package com.rest.edu.global.config;

import com.rest.edu.global.filter.APIKeyAuthenticationFilter;
import com.rest.edu.global.filter.ApiKeyAuthManager;
import com.rest.edu.global.filter.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(apiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                    .formLogin().disable()
                .build();
    }

    @Bean
    public APIKeyAuthenticationFilter apiKeyAuthenticationFilter() {
        APIKeyAuthenticationFilter filter = new APIKeyAuthenticationFilter();
        ApiKeyAuthManager apiKeyAuthManager = new ApiKeyAuthManager();

        filter.setAuthenticationManager(apiKeyAuthManager);
        return filter;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}
