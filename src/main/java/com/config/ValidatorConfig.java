package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * This is {@link ValidatorConfig} class for setting up the validator
 * for Entities.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Configuration
public class ValidatorConfig {

    /**
     * This method create bean for validating entities in application.
     *
     * @return {@link Validator}.
     */
    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
