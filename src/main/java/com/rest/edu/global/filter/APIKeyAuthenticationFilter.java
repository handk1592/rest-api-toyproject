package com.rest.edu.global.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class APIKeyAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String API_AUTH_KEY_HEADER_VALUE = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(API_AUTH_KEY_HEADER_VALUE);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}