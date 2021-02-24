package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This is configuration class for configuring
 * {@link BCryptPasswordEncoder} class.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Configuration
public class EncoderConfig {

    /**
     * This bean create {@link BCryptPasswordEncoder} it'll be use
     * for data encryption.
     *
     * @return {@link BCryptPasswordEncoder} class.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
