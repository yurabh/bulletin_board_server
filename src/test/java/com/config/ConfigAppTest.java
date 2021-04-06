package com.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Class {@link ConfigAppTest} set up all the configurations,
 * makes connection with database,manages transactions.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Configuration
@ComponentScan(basePackages = {
        "com.service",
        "com.dao",
        "com.controller",
        "com.exception.handler",
        "com.mapper",
        "com.security"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.repository")
@Import({EmailConfig.class,
        ModelMapperConfig.class,
        ValidatorConfig.class,
        EncoderConfig.class})
@PropertySource("classpath:db.properties")
public class ConfigAppTest implements
        WebMvcConfigurer, EnvironmentAware {


    /**
     * This is class {@link Environment} for load properties from file
     * and use them for get properties from files.
     */
    private Environment env;


    /**
     * This is method for setting {@link Environment}
     * to the field Environment in this class.
     *
     * @param environment {@link Environment}.
     */
    @Override
    public void setEnvironment(final Environment environment) {
        env = environment;
    }


    /**
     * This method binds a JPA {@link javax.persistence.EntityManager}
     * from the specified factory to the thread, potentially allowing
     * for one thread-bound {@link javax.persistence.EntityManager} per factory.
     * This transaction manager is appropriate for applications that use
     * a single JPA {@link EntityManagerFactory} for transactional data access.
     * It helps to open and close transactions.
     *
     * @param factory    {@link EntityManagerFactory} provides an efficient
     *                   way to construct multiple
     *                   {@link javax.persistence.EntityManager}
     *                   instances for a database.
     * @param dataSource {@link DataSource} provides sets up a location where
     *                   data that is being used originates from.
     * @return {@link PlatformTransactionManager} that is responsible for
     * transactional data access.
     */
    @Bean
    public PlatformTransactionManager transactionManager(
            final EntityManagerFactory factory,
            final DataSource dataSource) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(factory);
        manager.setDataSource(dataSource);
        return manager;
    }


    /**
     * This method sets up a location where data that is being used
     * originates from.
     *
     * @return {@link DataSource} is the location where data that is
     * being used originates from.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.
                getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url_test"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
    }


    /**
     * This method produces a container-managed
     * {@link EntityManagerFactory}.
     *
     * @param dataSource       {@link DataSource}
     * @param jpaVendorAdapter {@link JpaVendorAdapter}
     * @return {@link LocalContainerEntityManagerFactoryBean}
     * that supports links to an existing JDBC {@link DataSource},
     * supports both local and global transactions.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            final DataSource dataSource,
            final JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean managerFactory =
                new LocalContainerEntityManagerFactoryBean();
        managerFactory.setDataSource(dataSource);
        managerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        managerFactory.setPackagesToScan("com.domain");
        return managerFactory;
    }


    /**
     * This method allows to plug in vendor-specific behavior into Spring's
     * {@link EntityManagerFactory} creators.
     * Serves as single config point for all vendor-specific properties.
     *
     * @return {@link JpaVendorAdapter} that allows to plug in vendor-specific
     * behavior into Spring's.
     * {@link EntityManagerFactory} creators.
     */
    @Bean
    public JpaVendorAdapter adapter() {
        HibernateJpaVendorAdapter jpaAdapter =
                new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabase(Database.MYSQL);
        jpaAdapter.setShowSql(Boolean.valueOf(env.
                getRequiredProperty("hibernate.show_sql")));
        jpaAdapter.setDatabasePlatform(env.
                getRequiredProperty("hibernate.dialect"));
        jpaAdapter.setGenerateDdl(Boolean.valueOf(env.
                getRequiredProperty("hibernate.show_ddl")));
        return jpaAdapter;
    }


    /**
     * This method gets {@link PersistenceAnnotationBeanPostProcessor}
     * that processes PersistenceUnit and PersistenceContext annotations,
     * for injection of the corresponding JPA resources.
     * {@link EntityManagerFactory} and {@link javax.persistence.EntityManager}.
     *
     * @return {@link PersistenceAnnotationBeanPostProcessor}.
     */
    @Bean
    public PersistenceAnnotationBeanPostProcessor postProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
