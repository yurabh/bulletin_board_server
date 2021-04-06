package com.security.jwt;

import com.constant.SecurityConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is class {@link JwtAuthenticationEntryPoint} which rejects every
 * unauthenticated request and sends error code 401.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    /**
     * This is method for configure and send users some exception
     * and information about request not perform and authentication
     * unsuccessful.
     *
     * @param req           {@link HttpServletRequest}.
     * @param resp          {@link HttpServletResponse}.
     * @param authException {@link AuthenticationException}.
     * @throws IOException can throw exception.
     */
    @Override
    public void commence(final HttpServletRequest req,
                         final HttpServletResponse resp,
                         final AuthenticationException authException)
            throws IOException {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                SecurityConstant.UNAUTHORIZED);
    }
}
