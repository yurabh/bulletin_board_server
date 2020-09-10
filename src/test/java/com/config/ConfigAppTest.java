package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class {@link ConfigAppTest} set up all the configurations,makes connection with database,
 * manages transactions and defines strategy for sending emails
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Configuration
@ComponentScan(basePackages = {"com.service", "com.dao", "com.controller"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.repository")
@EnableScheduling
public class ConfigAppTest implements WebMvcConfigurer {


    /**
     * This method binds a JPA {@link EntityManager} from the specified factory to the thread, potentially allowing
     * for one thread-bound {@link EntityManager} per factory.
     * This transaction manager is appropriate for applications that use a single JPA {@link EntityManagerFactory}
     * for transactional data access.It helps to open and close transactions
     *
     * @param factory    {@link EntityManagerFactory} provides an efficient way to construct multiple
     *                   {@link EntityManager} instances for a database.
     * @param dataSource {@link DataSource} provides sets up a location where data that is being used originates from
     * @return {@link PlatformTransactionManager} that is responsible for transactional data access
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory, DataSource dataSource) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(factory);
        manager.setDataSource(dataSource);
        return manager;
    }


    /**
     * This method sets up a location where data that is being used originates from
     *
     * @return {@link DataSource} is the location where data that is being used originates from.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bulletin_board_test?serverTimezone=Europe/Kiev");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }


    /**
     * This method produces a container-managed {@link EntityManagerFactory}
     *
     * @param dataSource       {@link DataSource}
     * @param jpaVendorAdapter {@link JpaVendorAdapter}
     * @return {@link LocalContainerEntityManagerFactoryBean} that supports links to an existing JDBC {@link DataSource},
     * supports both local and global transactions
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter
            jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean managerFactory = new LocalContainerEntityManagerFactoryBean();
        managerFactory.setDataSource(dataSource);
        managerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        managerFactory.setPackagesToScan("com.domain");
        return managerFactory;
    }


    /**
     * This method allows to plug in vendor-specific behavior into Spring's {@link EntityManagerFactory} creators.
     * Serves as single configuration point for all vendor-specific properties
     *
     * @return {@link JpaVendorAdapter} that allows to plug in vendor-specific behavior into Spring's
     * {@link EntityManagerFactory} creators
     */
    @Bean
    public JpaVendorAdapter adapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        adapter.setGenerateDdl(false);
        return adapter;
    }


    /**
     * This method gets {@link PersistenceAnnotationBeanPostProcessor} that processes PersistenceUnit
     * and PersistenceContext annotations,for injection of the corresponding JPA resources
     * {@link EntityManagerFactory} and {@link EntityManager}
     *
     * @return {@link PersistenceAnnotationBeanPostProcessor}
     */
    @Bean
    public PersistenceAnnotationBeanPostProcessor postProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }


    /**
     * This method defines a strategy for sending simple mails
     *
     * @return {@link JavaMailSender} that used in conjunction with the {@link MimeMessageHelper}
     * class for convenient creation of JavaMailMimeMessages,including attachments
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("");
        mailSender.setPassword("");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
