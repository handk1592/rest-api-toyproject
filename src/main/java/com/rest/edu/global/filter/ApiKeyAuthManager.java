package com.rest.edu.global.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ApiKeyAuthManager implements AuthenticationManager {

    private static final String API_AUTH_KEY_HEADER_VALUE = "test123";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String requestAuthorization = (String) authentication.getPrincipal();

        if ( StringUtils.isEmpty(requestAuthorization) || !StringUtils.startsWith(requestAuthorization, "Bearer ") ) {
            throw new BadCredentialsException("The API key was not found or not the expected value.");
        } else {
            String apiKey = StringUtils.remove(requestAuthorization, "Bearer ");

            if (!StringUtils.equals(API_AUTH_KEY_HEADER_VALUE, apiKey)) {
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            }

            authentication.setAuthenticated(true);
        }

        return authentication;
    }
}
