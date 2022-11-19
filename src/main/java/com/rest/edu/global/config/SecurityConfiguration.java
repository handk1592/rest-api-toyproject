package com.rest.edu.global.config;

import com.rest.edu.global.filter.APIKeyAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration {

    private String principalRequestHeader = "Authorization";
    private String principalRequestValue = "test123";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(apiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().disable()
                .build();
    }

    @Bean
    public APIKeyAuthenticationFilter apiKeyAuthenticationFilter() {
        APIKeyAuthenticationFilter filter = new APIKeyAuthenticationFilter(principalRequestHeader);

        filter.setAuthenticationManager(authentication -> {
            String requestAuthorization = (String) authentication.getPrincipal();

            if ( StringUtils.isEmpty(requestAuthorization) || !StringUtils.startsWith(requestAuthorization, "Bearer ") ) {
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            } else {
                String apiKey = StringUtils.remove(requestAuthorization, "Bearer ");

                if (StringUtils.equals(principalRequestValue, apiKey))
                    authentication.setAuthenticated(true);
            }

            return authentication;
        });

        return filter;
    }
}
