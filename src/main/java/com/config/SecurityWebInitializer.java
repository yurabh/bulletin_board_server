package com.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Class {@link SecurityWebInitializer}
 * extend {@link AbstractSecurityWebApplicationInitializer}
 * to ensure web security.
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class SecurityWebInitializer
        extends AbstractSecurityWebApplicationInitializer {

    /**
     * This is constructor which transfers the file
     * {@link SecurityConfig} to the super class
     * of this class to ensure security configuration.
     */
    public SecurityWebInitializer() {
        super(SecurityConfig.class);
    }
}
