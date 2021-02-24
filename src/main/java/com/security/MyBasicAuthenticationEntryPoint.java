package com.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is class {@link MyBasicAuthenticationEntryPoint} which is provides
 * basic authentication entry point.
 */

@Component
public class MyBasicAuthenticationEntryPoint extends
        BasicAuthenticationEntryPoint {

    /**
     * This is method which is configure basic authentication entry points.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @param authEx   {@link AuthenticationException}.
     * @throws IOException can throw exception.
     */
    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authEx)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic name: "
                + getRealmName() + "");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    /**
     * This is method after properties set.
     */
    @Override
    public void afterPropertiesSet() {
        setRealmName("Bulletin");
        super.afterPropertiesSet();
    }
}
