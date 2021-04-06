package com.security.jwt;

import com.constant.SecurityConstant;
import com.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is class {@link JwtFilter} it helps us to perform some
 * actions with Jwt etc like verify whether token is valid and
 * and get author name from token.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * This is field {@link MyUserDetailsService}.
     */
    private final MyUserDetailsService service;

    /**
     * This is field {@link JwtProvider}.
     */
    private final JwtProvider jwtProvider;

    /**
     * This is constructor with parameters which's inject two
     * objects.
     *
     * @param serviceMyUserDetails {@link MyUserDetailsService}.
     * @param providerJwt          {@link JwtProvider}.
     */
    @Autowired
    public JwtFilter(final MyUserDetailsService serviceMyUserDetails,
                     final JwtProvider providerJwt) {
        this.service = serviceMyUserDetails;
        this.jwtProvider = providerJwt;
    }

    /**
     * This is method which's get token from header and also
     * get user name from the data base and verify whether token
     * is valid and other manipulation.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link  HttpServletResponse}.
     * @param chain    {@link  HttpServletResponse}.
     * @throws ServletException {@link ServletException} can throw.
     * @throws IOException      {@link IOException} can throw.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.
                getHeader(SecurityConstant.AUTHORIZATION);
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.
                startsWith(SecurityConstant.REQUEST_TOKEN_HEADER)) {
            jwtToken = requestTokenHeader.
                    substring(SecurityConstant.NUMBER_SEVEN);
            username = jwtProvider.getUsernameFromToken(jwtToken);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        if (username != null && SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(username);
            final Boolean isValidate = jwtProvider.
                    validateToken(jwtToken, userDetails);
            if (isValidate.equals(true)) {
                UsernamePasswordAuthenticationToken
                        usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().
                        setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
