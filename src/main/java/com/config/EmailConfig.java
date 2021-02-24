package com.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Class {@link EmailConfig} set up all the configurations,and defines strategy
 * for sending emails.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Configuration
@PropertySource("classpath:mail.properties")
public class EmailConfig implements EnvironmentAware {


    /**
     * This is class {@link Environment} for load properties from file and
     * use them in configuration for sending email.
     */
    private Environment env;


    /**
     * This is method which set's class {@link Environment}
     * and helps to get data about mail configuration
     * for mailing.
     *
     * @param environment {@link Environment}.
     */
    @Override
    public void setEnvironment(final Environment environment) {
        this.env = environment;
    }


    /**
     * This method defines a strategy for sending simple mails.
     *
     * @return {@link JavaMailSender} that used in conjunction with the
     * {@link  org.springframework.mail.javamail.MimeMessageHelper} class
     * for convenient creation ofJavaMailMimeMessages,including attachments.
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getRequiredProperty("host"));
        mailSender.setPort(Integer.parseInt(env.getRequiredProperty("port")));
        mailSender.setUsername(env.getRequiredProperty("user_name"));
        mailSender.setPassword(env.getRequiredProperty("mail_password"));
        Properties props = mailSender.getJavaMailProperties();
        props.put(env.getProperty("tran_prop_key"),
                env.getProperty("tran_prop_value"));
        props.put(env.getProperty("auth_key"), env.getProperty("auth_value"));
        props.put(env.getProperty("ssl_key"), env.getProperty("ssl_value"));
        props.put(env.getProperty("start_enable_key"),
                env.getProperty("start_enable_value"));
        return mailSender;
    }
}
